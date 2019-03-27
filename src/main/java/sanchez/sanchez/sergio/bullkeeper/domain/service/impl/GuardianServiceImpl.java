package sanchez.sanchez.sergio.bullkeeper.domain.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;

import io.jsonwebtoken.lang.Assert;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IDeviceGroupsService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IGuardianService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IKidService;
import sanchez.sanchez.sergio.bullkeeper.exception.EmailAlreadyExistsException;
import sanchez.sanchez.sergio.bullkeeper.exception.GuardianNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.mapper.GuardianEntityMapper;
import sanchez.sanchez.sergio.bullkeeper.mapper.KidEntityMapper;
import sanchez.sanchez.sergio.bullkeeper.mapper.PreferencesEntityMapper;
import sanchez.sanchez.sergio.bullkeeper.mapper.SupervisedChildrenEntityMapper;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.GuardianEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.GuardianRolesEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.KidEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SupervisedChildrenEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.PreferencesEntity.RemoveAlertsEveryEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.GuardianRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.KidRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.SchoolRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.SupervisedChildrenRepository;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.RegisterGuardianByFacebookDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.RegisterGuardianByGoogleDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.RegisterGuardianDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.RegisterKidDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveUserSystemPreferencesDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.UpdateGuardianDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.UpdateKidDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.ChildrenOfGuardianDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.GuardianDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.KidDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.UserSystemPreferencesDTO;
import sanchez.sanchez.sergio.bullkeeper.web.uploads.service.IUploadFilesService;

/**
 * Guardian Service
 * @author sergiosanchezsanchez
 *
 */
@Service
public class GuardianServiceImpl implements IGuardianService {

    private static Logger logger = LoggerFactory.getLogger(GuardianServiceImpl.class);

    private final GuardianRepository guardianRepository;
    private final GuardianEntityMapper guardianEntityMapper;
    private final KidEntityMapper kidEntityMapper;
    private final KidRepository kidRepository;
    private final PasswordEncoder passwordEncoder;
    private final SchoolRepository schoolRepository;
    private final PreferencesEntityMapper preferencesEntityMapper;
    private final IKidService kidService;
    private final IUploadFilesService uploadFilesService;
    private final IDeviceGroupsService deviceGroupsService;
    private final SupervisedChildrenRepository supervisedChildrenRepository;
    private final SupervisedChildrenEntityMapper supervisedChildrenEntityMapper;

    /**
     * 
     * @param guardianRepository
     * @param guardianEntityMapper
     * @param kidEntityMapper
     * @param kidRepository
     * @param passwordEncoder
     * @param schoolRepository
     * @param preferencesEntityMapper
     * @param kidService
     * @param uploadFilesService
     * @param deviceGroupsService
     * @param supervisedChildrenRepository
     */
    @Autowired
    public GuardianServiceImpl(final GuardianRepository guardianRepository, final GuardianEntityMapper parentEntityMapper,
    		final KidEntityMapper sonEntityMapper, final KidRepository kidRepository, 
    		final PasswordEncoder passwordEncoder, final SchoolRepository schoolRepository, 
    		final PreferencesEntityMapper preferencesEntityMapper, final IKidService kidService,
    		final IUploadFilesService uploadFilesService, final IDeviceGroupsService deviceGroupsService,
    		final SupervisedChildrenRepository supervisedChildrenRepository,
    		final SupervisedChildrenEntityMapper supervisedChildrenEntityMapper) {
        super();
        this.guardianRepository = guardianRepository;
        this.guardianEntityMapper = parentEntityMapper;
        this.kidEntityMapper = sonEntityMapper;
        this.kidRepository = kidRepository;
        this.passwordEncoder = passwordEncoder;
        this.schoolRepository = schoolRepository;
        this.preferencesEntityMapper = preferencesEntityMapper;
        this.kidService = kidService;
        this.uploadFilesService = uploadFilesService;
        this.deviceGroupsService = deviceGroupsService;
        this.supervisedChildrenRepository = supervisedChildrenRepository;
        this.supervisedChildrenEntityMapper = supervisedChildrenEntityMapper; 
    }

    /**
     * Find Paginated
     * @param page
     * @param size
     */
    @Override
    public Page<GuardianDTO> findPaginated(Integer page, Integer size) {
        Pageable pageable = new PageRequest(page, size);
        Page<GuardianEntity> parentsPage = guardianRepository.findAll(pageable);
        return parentsPage.map(new Converter<GuardianEntity, GuardianDTO>() {
            @Override
            public GuardianDTO convert(GuardianEntity parent) {
                return guardianEntityMapper.guardianEntityToGuardianDTO(parent);
            }
        });
    }

    /**
     * Find Paginated
     * @param pageable
     */
    @Override
    public Page<GuardianDTO> findPaginated(Pageable pageable) {
        Page<GuardianEntity> parentsPage = guardianRepository.findAll(pageable);
        return parentsPage.map(new Converter<GuardianEntity, GuardianDTO>() {
            @Override
            public GuardianDTO convert(GuardianEntity parent) {
                return guardianEntityMapper.guardianEntityToGuardianDTO(parent);
            }
        });
    }

    /**
     * Get Guardian By Id
     * @param id
     */
    @Override
    public GuardianDTO getGuardianById(final String id) {
        GuardianEntity parentEntity = guardianRepository.findOne(new ObjectId(id));
        return guardianEntityMapper.guardianEntityToGuardianDTO(parentEntity);
    }

    /**
     * Get Kids Of Guardian
     * @param id
     */
    @Override
    public ChildrenOfGuardianDTO getKidsOfGuardian(String id) {
    	Assert.notNull(id, "Id can not be null");
    	return getKidsOfGuardian(id, null);
    }
    
    /**
     * Get Kids Of Guardian
     * @param id
     * @param patternText
     */
    @Override
	public ChildrenOfGuardianDTO getKidsOfGuardian(final String id, final String patternText) {
    	Assert.notNull(id, "Id can not be null");
    	
    	
    	// Get Supervised Children
    	final Iterable<SupervisedChildrenEntity> supervisedChildrenEntities = 
    			supervisedChildrenRepository.findByGuardianIdAndIsConfirmedTrue(new ObjectId(id))
    			.stream().filter(supervisedChildren -> patternText == null || patternText.isEmpty() || 
    					supervisedChildren.getKid().getFullName()
    					.toLowerCase().contains(patternText.toLowerCase()))
    			.collect(Collectors.toList());
    								
    	final ChildrenOfGuardianDTO childrenOfGuardianDTO = new ChildrenOfGuardianDTO();
    	// Set Total
    	childrenOfGuardianDTO.setTotal(supervisedChildrenRepository
    			.countByGuardianId(new ObjectId(id)));
    	// Set No Confirmed
    	childrenOfGuardianDTO.setNoConfirmed(supervisedChildrenRepository
    			.countByGuardianIdAndIsConfirmedFalse(new ObjectId(id)));
    	// Set Confirmed
    	childrenOfGuardianDTO.setConfirmed(supervisedChildrenRepository
    			.countByGuardianIdAndIsConfirmedTrue(new ObjectId(id)));
    	// Set Supervised Children
    	childrenOfGuardianDTO.setSupervisedChildren(
    			supervisedChildrenEntityMapper.supervisedChildrenEntitiesToSupervisedChildrenDTOs(supervisedChildrenEntities));
    	
    	return childrenOfGuardianDTO;
    	
    
	}

    /**
     * Save
     * @param registerGuardian
     */
    @Override
    public GuardianDTO save(final RegisterGuardianDTO registerGuardian) {
        final GuardianEntity guardianToSave = guardianEntityMapper.registerGuardianDTOToGuardianEntity(registerGuardian);
        if(guardianRepository.countByEmail(guardianToSave.getEmail()) > 0)
        	throw new EmailAlreadyExistsException();
        
        final GuardianEntity guardianSaved = guardianRepository.save(guardianToSave);
        logger.debug("Guardian Saved -> " + guardianSaved.toString());
        return guardianEntityMapper.guardianEntityToGuardianDTO(guardianSaved);
    }

    /**
     * Save
     * @param registerGuardian
     */
    @Override
    public GuardianDTO save(final RegisterGuardianByFacebookDTO registerGuardian) {
    	logger.debug("Register Guardian by facebook");
        final GuardianEntity guardianToSave = guardianEntityMapper.registerGuardianByFacebookDTOToGuardianEntity(registerGuardian);
        if(guardianRepository.countByEmail(guardianToSave.getEmail()) > 0)
        	throw new EmailAlreadyExistsException();
        final GuardianEntity guardianSaved = guardianRepository.save(guardianToSave);
        logger.debug("Guardian Saved -> " + guardianSaved.toString());
        return guardianEntityMapper.guardianEntityToGuardianDTO(guardianSaved);
    }
    
    /**
     * Save
     * @param registerGuardian
     */
    @Override
    public GuardianDTO save(final RegisterGuardianByGoogleDTO registerGuardian) {
    	logger.debug("Register Guardian by google");
        final GuardianEntity guardianToSave = guardianEntityMapper.registerGuardianByGoogleDTOToGuardianEntity(registerGuardian);
        if(guardianRepository.countByEmail(guardianToSave.getEmail()) > 0)
        	throw new EmailAlreadyExistsException();
        final GuardianEntity guardianSaved = guardianRepository.save(guardianToSave);
        logger.debug("Guardian Saved -> " + guardianSaved.toString());
        return guardianEntityMapper.guardianEntityToGuardianDTO(guardianSaved);
    }

    /**
     * Add Kid
     * @param guardian
     * @parma regiterKidDTO
     */
    @Override
    public KidDTO addKid(final String guardian, final RegisterKidDTO regiterKidDTO) {
    	Assert.notNull(guardian, "Guardian can not be null");
    	Assert.notNull(regiterKidDTO, "Register DTO can not be null"); 	
    	
    	// Get Guardian
        final GuardianEntity guardianEntity = guardianRepository.findOne(new ObjectId(guardian));
    	
    	// Get Kid
        final KidEntity kidToAdd = kidEntityMapper.registerKidDTOToKidEntity(regiterKidDTO);
        
        // Save Kid
        final KidEntity kidSaved = kidRepository.save(kidToAdd);
      
        // Save Relation
        final SupervisedChildrenEntity supervisedChildrenEntityToSave = new SupervisedChildrenEntity();
        supervisedChildrenEntityToSave.setKid(kidSaved);
        supervisedChildrenEntityToSave.setGuardian(guardianEntity);
        supervisedChildrenEntityToSave.setConfirmed(true);
        supervisedChildrenEntityToSave.setRole(GuardianRolesEnum.ADMIN);
        // Save Supervised Children
        final SupervisedChildrenEntity supervisedChildrenEntitySaved = 
        		supervisedChildrenRepository.save(supervisedChildrenEntityToSave);
        
        // Return Kid
        return kidEntityMapper.kidEntityToKidDTO(supervisedChildrenEntitySaved.getKid());
    }

    /**
     * Update Kid
     * @param guardian
     * @param updateKidDTO
     */
    @Override
    public KidDTO updateKid(String guardian, UpdateKidDTO updateKidDTO) {
        KidEntity sonEntityToUpdate = kidRepository.findOne(new ObjectId(updateKidDTO.getIdentity()));
        sonEntityToUpdate.setFirstName(updateKidDTO.getFirstName());
        sonEntityToUpdate.setLastName(updateKidDTO.getLastName());
        sonEntityToUpdate.setBirthdate(updateKidDTO.getBirthdate());
        sonEntityToUpdate.setSchool(schoolRepository.findOne(new ObjectId(updateKidDTO.getSchool())));
        KidEntity sonEntityUpdated = kidRepository.save(sonEntityToUpdate);
        return kidEntityMapper.kidEntityToKidDTO(sonEntityUpdated);
    }

    /**
     * Get Guardian By Id
     * @param id
     */
    @Override
    public GuardianDTO getGuardianById(final ObjectId id) {
        GuardianEntity parentEntity = guardianRepository.findOne(id);
        return guardianEntityMapper.guardianEntityToGuardianDTO(parentEntity);
    }

    /**
     * Set As Not Active and Confirmation Token
     * @param id
     * @param confirmationToken
     */
    @Override
    public void setAsNotActiveAndConfirmationToken(final String id, final String confirmationToken) {
        guardianRepository.setAsNotActiveAndConfirmationToken(new ObjectId(id),
        		confirmationToken);
    }

    /**
     * Get Guardian By Email
     * @param email
     */
    @Override
    public GuardianDTO getGuardianByEmail(final String email) {
        GuardianEntity parentEntity = (GuardianEntity) guardianRepository.findOneByEmail(email);
        return guardianEntityMapper.guardianEntityToGuardianDTO(parentEntity);
    }

    /**
     * Update
     * @param id
     * @param updateGuardianDTO
     */
    @Override
    public GuardianDTO update(final ObjectId id, final UpdateGuardianDTO updateGuardianDTO) {

        final GuardianEntity guardianToUpdate = guardianRepository.findOne(id);
        // update parent
        guardianToUpdate.setFirstName(updateGuardianDTO.getFirstName());
        guardianToUpdate.setLastName(updateGuardianDTO.getLastName());
        guardianToUpdate.setBirthdate(updateGuardianDTO.getBirthdate());
        guardianToUpdate.setTelephone(PhoneNumberUtil.getInstance()
        		.format(updateGuardianDTO.getTelephone(), PhoneNumberFormat.E164));
        guardianToUpdate.setVisible(updateGuardianDTO.isVisible());
       
        final GuardianEntity guardianUpdated = guardianRepository.save(guardianToUpdate);
        return guardianEntityMapper.guardianEntityToGuardianDTO(guardianUpdated);
    }

    /**
     * Change User Password
     * @param id
     * @param newPassword
     */
    @Override
    public void changeUserPassword(final ObjectId id, final String newPassword) {
        guardianRepository.setNewPassword(id, passwordEncoder.encode(newPassword));
    }

    /**
     * Active Account
     * @param token
     */
    @Override
    public Boolean activateAccount(final String token) {
        Assert.notNull(token, "Token can not be null");
        Assert.hasLength(token, "Token can not be empty");

        Boolean isActivated = Boolean.FALSE;
        Boolean exists = guardianRepository.countByConfirmationToken(token) == 1
                ? Boolean.TRUE : Boolean.FALSE;

        if (exists) {
            guardianRepository.setActiveAsTrueAndDeleteConfirmationToken(token);
            isActivated = Boolean.TRUE;
        }

        return isActivated;
    }

    /**
     * Lock Account
     * @param id
     */
    @Override
    public void lockAccount(final String id) {
        guardianRepository.lockAccount(new ObjectId(id));
    }

    /**
     * UnLock Account
     * @param id
     */
    @Override
    public void unlockAccount(final String id) {
        guardianRepository.unlockAccount(new ObjectId(id));
    }

    /**
     * Get Guardian By FB Id
     * @param fbId
     */
    @Override
    public GuardianDTO getGuardianByFbId(final String fbId) {
        return guardianEntityMapper.guardianEntityToGuardianDTO(
        		guardianRepository.findByFbId(fbId));
    }
    
    /**
     * Get Guardian By Google Id
     * @param googleId
     */
    @Override
	public GuardianDTO getGuardianByGoogleId(final String googleId) {
    	 return guardianEntityMapper.guardianEntityToGuardianDTO(
    			 guardianRepository.findByGoogleId(googleId));
	}

    /**
     * Update FB Access Token
     * @param fbId
     * @param fbAccessToken
     */
    @Override
    public void updateFbAccessToken(final String fbId, final String fbAccessToken) {
        guardianRepository.setFbAccessTokenByFbId(fbAccessToken, fbId);
    }

    /**
     * Delete Account
     * @param confirmationToken
     */
    @Override
    public void deleteAccount(final String confirmationToken) {
    	Assert.notNull(confirmationToken, "Confirmation Token can not be null");
    	
    	// Get Guardian by confirmation token
    	final GuardianEntity guardian = guardianRepository
    			.findByConfirmationToken(confirmationToken);
    	// Guardian not found exception
    	if(guardian == null)
    		throw new GuardianNotFoundException();
    	
    	
		// Get Supervised Children Entities
		final List<SupervisedChildrenEntity> supervisedChildrenEntities = 
				this.supervisedChildrenRepository.findByGuardianId(guardian.getId());
		
		for(final SupervisedChildrenEntity supervisedChildren: 
			supervisedChildrenEntities) {
			
			if(supervisedChildren.isConfirmed() && 
					supervisedChildren.getRole().equals(GuardianRolesEnum.ADMIN)) {
				
				final long countSupervisedChildren = supervisedChildrenRepository.countByKidIdAndRoleAndIsConfirmed(supervisedChildren.getKid().getId(), 
						GuardianRolesEnum.ADMIN, true);
				
				if(countSupervisedChildren == 1) {
					// Delete Kid By Id
					kidService.deleteById(supervisedChildren.getKid().getId());
				}
			}
		}
			
		// Delete Relations
		supervisedChildrenRepository.delete(supervisedChildrenEntities);
		
		final String imageId = guardianRepository
			.getGuardianImageIdByUserId(guardian.getId());
		
		if(imageId != null && !imageId.isEmpty())
			// Delete Image
	    	uploadFilesService.deleteImage(imageId);
    	
    	// Delete Guardian
		guardianRepository.delete(guardian);
		
		// Remove Device Group
		deviceGroupsService.removeDeviceGroupOf(guardian.getId());
		
    }

    /**
     * Cancel Account Deletion Process
     * @param confirmationToken
     */
    @Override
    public void cancelAccountDeletionProcess(final String confirmationToken) {
    	
    	Long exists = guardianRepository.countByConfirmationToken(confirmationToken);
    	if(exists == 0)
    		throw new GuardianNotFoundException();
    	
        guardianRepository.setPendingDeletionAsFalseAndDeleteConfirmationToken(confirmationToken);
    }

    /**
     * Start Account Deletion Process
     * @param id
     * @param confirmationToken
     */
    @Override
    public void startAccountDeletionProcess(final ObjectId id, final String confirmationToken) {
        guardianRepository.setPendingDeletionAsTrueAndConfirmationTokenById(id, confirmationToken);
    }

    
    /**
     * Delete Unactivated Accounts
     */
    @Override
    public Long deleteUnactivatedAccounts() {
        return guardianRepository.deleteByActiveFalse();
    }

    /**
     * Cancel All Account Deletion Process
     */
    @Override
    public void cancelAllAccountDeletionProcess() {
        guardianRepository.setPendingDeletionAsFalseAndDeleteConfirmationToken();
    }

    /**
     * Update Last Access To Alerts
     * @param id
     */
    @Override
    public void updateLastAccessToAlerts(final ObjectId id) {
        guardianRepository.setLastAccessToAlerts(id);
    }

    /**
     * Get Profile Image
     * @param id
     */
    @Override
    public String getProfileImage(final ObjectId id) {
        return guardianRepository.getGuardianImageIdByUserId(id);
    }
    
    /**
     * Save Preferences
     * @param preferences
     * @param guardian
     */
    @Override
	public UserSystemPreferencesDTO savePreferences(final SaveUserSystemPreferencesDTO preferences, 
			final ObjectId guardian) {
    	Assert.notNull(preferences, "Preferences can not be null");
    	Assert.notNull(guardian, "guardian can not be null");
    	
    	logger.debug("Preferences to save -> " + preferences.toString());
    	
    	final GuardianEntity guardianEntity = guardianRepository.findOne(guardian);
    	guardianEntity.getPreferences().setPushNotificationsEnabled(preferences.isPushNotificationsEnabled());
    	guardianEntity.getPreferences().setRemoveAlertsEvery(RemoveAlertsEveryEnum.valueOf(preferences.getRemoveAlertsEvery()));
    	guardianRepository.save(guardianEntity);
    	return preferencesEntityMapper.preferencesEntityToUserSystemPreferencesDTO(guardianEntity.getPreferences());
    	
	}
    
    /**
     * Get Prefences
     * @param guardian
     */
    @Override
	public UserSystemPreferencesDTO getPreferences(final ObjectId guardian) {
    	Assert.notNull(guardian, "Id Parent can not be null");
    	return preferencesEntityMapper.preferencesEntityToUserSystemPreferencesDTO(guardianRepository.getPreferences(guardian));
	}
    
    /**
     * Search Guardians
     * @param text
     * @param exclude
     */
    @Override
	public Iterable<GuardianDTO> search(final String text, final List<ObjectId> exclude) {
    	Assert.notNull(text, "Text can not be null");
    	Assert.notNull(exclude, "exclude can not be null");
    	final List<GuardianEntity> guardians = guardianRepository.search(text, exclude);
    	return guardianEntityMapper.guardianEntitiesToGuardianDTOs(guardians);
	}

    /**
     * Change Email
     * @param currentEmail
     * @param newEmail
     */
    @Override
	public void changeEmail(final String currentEmail, final String newEmail) {
    	Assert.notNull(currentEmail, "Current Email can not be null");
    	Assert.notNull(newEmail, "New Email can not be null");
		
    	guardianRepository.changeEmail(currentEmail, newEmail);
    	
	}
    
    /**
     * Change Password
     * @param guardian
     * @param newPassword
     */
    @Override
	public void changePassword(final ObjectId guardian, final String newPassword) {
    	Assert.notNull(guardian, "Guardian can not be null");
    	Assert.notNull(newPassword, "New Email can not be null");
		
    	guardianRepository.changePassword(guardian, newPassword);
	}
    
    /**
     * 
     */
    @PostConstruct
    protected void init() {
        Assert.notNull(guardianRepository, "Guardian Repository can not be null");
        Assert.notNull(guardianEntityMapper, "GUardian Entity Mapper can not be null");
        Assert.notNull(kidEntityMapper, "Kid Entity Mapper can not be null");
        Assert.notNull(kidRepository, "Kid Repository can not be null");
        Assert.notNull(passwordEncoder, "Password Encoder can not be null");
        Assert.notNull(schoolRepository, "School Repository can not be null");
        Assert.notNull(preferencesEntityMapper, "Preferences Entity Mapper can not be null");
        Assert.notNull(deviceGroupsService, "DeviceGroupsService can not be null");
    }
}
