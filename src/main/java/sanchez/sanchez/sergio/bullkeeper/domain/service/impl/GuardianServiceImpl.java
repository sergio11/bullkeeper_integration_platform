package sanchez.sanchez.sergio.bullkeeper.domain.service.impl;

import java.util.List;

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
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.GuardianDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.KidDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SupervisedChildrenDTO;
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
     */
    @Override
    public GuardianDTO getGuardianById(final String id) {
        GuardianEntity parentEntity = guardianRepository.findOne(new ObjectId(id));
        return guardianEntityMapper.guardianEntityToGuardianDTO(parentEntity);
    }

    /**
     * Get Kids Of Guardian
     */
    @Override
    public Iterable<SupervisedChildrenDTO> getKidsOfGuardian(String id) {
    	Assert.notNull(id, "Id can not be null");
    	
    	// Get Supervised Children
    	final Iterable<SupervisedChildrenEntity> supervisedChildrenEntities = supervisedChildrenRepository
    			.findByGuardianId(new ObjectId(id));
    	
    	return supervisedChildrenEntityMapper
    			.supervisedChildrenEntitiesToSupervisedChildrenDTOs(supervisedChildrenEntities);
    }

    /**
     * Save
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
     */
    @Override
    public KidDTO addKid(final String guardian, final RegisterKidDTO regiterKidDTO) {
    	Assert.notNull(guardian, "Guardian can not be null");
    	Assert.notNull(regiterKidDTO, "Register DTO can not be null");
    	// Get Kid
        final KidEntity kidToAdd = kidEntityMapper.registerKidDTOToKidEntity(regiterKidDTO);
        // Get Guardian
        final GuardianEntity guardianEntity = guardianRepository.findOne(new ObjectId(guardian));
        
        final SupervisedChildrenEntity supervisedChildrenEntityToSave = new SupervisedChildrenEntity();
        supervisedChildrenEntityToSave.setKid(kidToAdd);
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
     * 
     */
    @Override
    public GuardianDTO getGuardianById(final ObjectId id) {
        GuardianEntity parentEntity = guardianRepository.findOne(id);
        return guardianEntityMapper.guardianEntityToGuardianDTO(parentEntity);
    }

    /**
     * 
     */
    @Override
    public void setAsNotActiveAndConfirmationToken(String id, String confirmationToken) {
        guardianRepository.setAsNotActiveAndConfirmationToken(new ObjectId(id),
        		confirmationToken);
    }

    /**
     * Get Guardian By Email
     */
    @Override
    public GuardianDTO getGuardianByEmail(String email) {
        GuardianEntity parentEntity = (GuardianEntity) guardianRepository.findOneByEmail(email);
        return guardianEntityMapper.guardianEntityToGuardianDTO(parentEntity);
    }

    /**
     * 
     */
    @Override
    public GuardianDTO update(final ObjectId id, final UpdateGuardianDTO updateGuardianDTO) {

        final GuardianEntity guardianToUpdate = guardianRepository.findOne(id);
        // update parent
        guardianToUpdate.setFirstName(updateGuardianDTO.getFirstName());
        guardianToUpdate.setLastName(updateGuardianDTO.getLastName());
        guardianToUpdate.setEmail(updateGuardianDTO.getEmail());
        guardianToUpdate.setBirthdate(updateGuardianDTO.getBirthdate());
        guardianToUpdate.setTelephone(PhoneNumberUtil.getInstance().format(updateGuardianDTO.getTelephone(), PhoneNumberFormat.E164));

        final GuardianEntity guardianUpdated = guardianRepository.save(guardianToUpdate);
        return guardianEntityMapper.guardianEntityToGuardianDTO(guardianUpdated);
    }

    /**
     * 
     */
    @Override
    public void changeUserPassword(final ObjectId id, final String newPassword) {
        guardianRepository.setNewPassword(id, passwordEncoder.encode(newPassword));
    }

    /**
     * 
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
     */
    @Override
    public void lockAccount(final String id) {
        guardianRepository.lockAccount(new ObjectId(id));
    }

    /**
     * UnLock Account
     */
    @Override
    public void unlockAccount(final String id) {
        guardianRepository.unlockAccount(new ObjectId(id));
    }

    /**
     * Get Guardian By FB Id
     */
    @Override
    public GuardianDTO getGuardianByFbId(String fbId) {
        return guardianEntityMapper.guardianEntityToGuardianDTO(
        		guardianRepository.findByFbId(fbId));
    }
    
    /**
     * 
     */
    @Override
	public GuardianDTO getGuardianByGoogleId(String googleId) {
    	 return guardianEntityMapper.guardianEntityToGuardianDTO(
    			 guardianRepository.findByGoogleId(googleId));
	}

    /**
     * 
     */
    @Override
    public void updateFbAccessToken(String fbId, String fbAccessToken) {
        guardianRepository.setFbAccessTokenByFbId(fbAccessToken, fbId);
    }

    /**
     * Delete Account
     */
    @Override
    public void deleteAccount(String confirmationToken) {
    	Assert.notNull(confirmationToken, "Confirmation Token can not be null");
    	
    	// Get Guardian by confirmation token
    	final GuardianEntity guardian = guardianRepository
    			.findByConfirmationToken(confirmationToken);
    	// Guardian not found exception
    	if(guardian == null)
    		throw new GuardianNotFoundException();
    	
    	// Delete Image
    	uploadFilesService.deleteImage(guardianRepository
    			.getGuardianImageIdByUserId(guardian.getId()));
    	
    	// Delete Guardian
		guardianRepository.delete(guardian);
		
		// Remove Device Group
		deviceGroupsService.removeDeviceGroupOf(guardian.getId());
		
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
    }

    /**
     * 
     */
    @Override
    public void cancelAccountDeletionProcess(String confirmationToken) {
    	
    	Long exists = guardianRepository.countByConfirmationToken(confirmationToken);
    	if(exists == 0)
    		throw new GuardianNotFoundException();
    	
        guardianRepository.setPendingDeletionAsFalseAndDeleteConfirmationToken(confirmationToken);
    }

    /**
     * 
     */
    @Override
    public void startAccountDeletionProcess(ObjectId id, String confirmationToken) {
        guardianRepository.setPendingDeletionAsTrueAndConfirmationTokenById(id, confirmationToken);
    }

    
    /**
     * 
     */
    @Override
    public Long deleteUnactivatedAccounts() {
        return guardianRepository.deleteByActiveFalse();
    }

    /**
     * 
     */
    @Override
    public void cancelAllAccountDeletionProcess() {
        guardianRepository.setPendingDeletionAsFalseAndDeleteConfirmationToken();
    }

    /**
     * 
     */
    @Override
    public void updateLastAccessToAlerts(ObjectId id) {
        guardianRepository.setLastAccessToAlerts(id);
    }

    /**
     * 
     */
    @Override
    public String getProfileImage(ObjectId id) {
        return guardianRepository.getGuardianImageIdByUserId(id);
    }
    
    /**
     * 
     */
    @Override
	public UserSystemPreferencesDTO savePreferences(SaveUserSystemPreferencesDTO preferences, 
			ObjectId guardian) {
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
     */
    @Override
	public UserSystemPreferencesDTO getPreferences(ObjectId idParent) {
    	Assert.notNull(idParent, "Id Parent can not be null");
    	return preferencesEntityMapper.preferencesEntityToUserSystemPreferencesDTO(guardianRepository.getPreferences(idParent));
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
