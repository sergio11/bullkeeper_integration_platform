package es.bisite.usal.bulltect.domain.service.impl;



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
import es.bisite.usal.bulltect.domain.service.IParentsService;
import es.bisite.usal.bulltect.mapper.ParentEntityMapper;
import es.bisite.usal.bulltect.mapper.PreferencesEntityMapper;
import es.bisite.usal.bulltect.mapper.SonEntityMapper;
import es.bisite.usal.bulltect.persistence.entity.ParentEntity;
import es.bisite.usal.bulltect.persistence.entity.SonEntity;
import es.bisite.usal.bulltect.persistence.repository.ParentRepository;
import es.bisite.usal.bulltect.persistence.repository.SchoolRepository;
import es.bisite.usal.bulltect.persistence.repository.SonRepository;
import es.bisite.usal.bulltect.web.dto.request.RegisterParentByFacebookDTO;
import es.bisite.usal.bulltect.web.dto.request.RegisterParentDTO;
import es.bisite.usal.bulltect.web.dto.request.RegisterSonDTO;
import es.bisite.usal.bulltect.web.dto.request.SaveUserSystemPreferencesDTO;
import es.bisite.usal.bulltect.web.dto.request.UpdateParentDTO;
import es.bisite.usal.bulltect.web.dto.request.UpdateSonDTO;
import es.bisite.usal.bulltect.web.dto.response.ParentDTO;
import es.bisite.usal.bulltect.web.dto.response.SonDTO;
import es.bisite.usal.bulltect.web.dto.response.UserSystemPreferencesDTO;
import es.bisite.usal.bulltect.web.rest.exception.EmailAlreadyExistsException;
import io.jsonwebtoken.lang.Assert;

@Service
public class ParentsServiceImpl implements IParentsService {

    private static Logger logger = LoggerFactory.getLogger(ParentsServiceImpl.class);

    private final ParentRepository parentRepository;
    private final ParentEntityMapper parentEntityMapper;
    private final SonEntityMapper sonEntityMapper;
    private final SonRepository sonRepository;
    private final PasswordEncoder passwordEncoder;
    private final SchoolRepository schoolRepository;
    private final PreferencesEntityMapper preferencesEntityMapper;

    @Autowired
    public ParentsServiceImpl(ParentRepository parentRepository, ParentEntityMapper parentEntityMapper, SonEntityMapper sonEntityMapper,
            SonRepository sonRepository, PasswordEncoder passwordEncoder, SchoolRepository schoolRepository, PreferencesEntityMapper preferencesEntityMapper) {
        super();
        this.parentRepository = parentRepository;
        this.parentEntityMapper = parentEntityMapper;
        this.sonEntityMapper = sonEntityMapper;
        this.sonRepository = sonRepository;
        this.passwordEncoder = passwordEncoder;
        this.schoolRepository = schoolRepository;
        this.preferencesEntityMapper = preferencesEntityMapper;
    }

    @Override
    public Page<ParentDTO> findPaginated(Integer page, Integer size) {
        Pageable pageable = new PageRequest(page, size);
        Page<ParentEntity> parentsPage = parentRepository.findAll(pageable);
        return parentsPage.map(new Converter<ParentEntity, ParentDTO>() {
            @Override
            public ParentDTO convert(ParentEntity parent) {
                return parentEntityMapper.parentEntityToParentDTO(parent);
            }
        });
    }

    @Override
    public Page<ParentDTO> findPaginated(Pageable pageable) {
        Page<ParentEntity> parentsPage = parentRepository.findAll(pageable);
        return parentsPage.map(new Converter<ParentEntity, ParentDTO>() {
            @Override
            public ParentDTO convert(ParentEntity parent) {
                return parentEntityMapper.parentEntityToParentDTO(parent);
            }
        });
    }

    @Override
    public ParentDTO getParentById(String id) {
        ParentEntity parentEntity = parentRepository.findOne(new ObjectId(id));
        return parentEntityMapper.parentEntityToParentDTO(parentEntity);
    }

    @Override
    public Iterable<SonDTO> getChildrenOfParent(String id) {
        Iterable<SonEntity> children = sonRepository.findByParentId(new ObjectId(id));
        return sonEntityMapper.sonEntitiesToSonDTOs(children);
    }

    @Override
    public ParentDTO save(RegisterParentDTO registerParent) {
        final ParentEntity parentToSave = parentEntityMapper.registerParentDTOToParentEntity(registerParent);
        if(parentRepository.countByEmail(parentToSave.getEmail()) > 0)
        	throw new EmailAlreadyExistsException();
        
        final ParentEntity parentSaved = parentRepository.save(parentToSave);
        return parentEntityMapper.parentEntityToParentDTO(parentSaved);
    }

    @Override
    public ParentDTO save(RegisterParentByFacebookDTO registerParent) {
        final ParentEntity parentToSave = parentEntityMapper.registerParentByFacebookDTOToParentEntity(registerParent);
        if(parentRepository.countByEmail(parentToSave.getEmail()) > 0)
        	throw new EmailAlreadyExistsException();
        final ParentEntity parentSaved = parentRepository.save(parentToSave);
        return parentEntityMapper.parentEntityToParentDTO(parentSaved);
    }

    @Override
    public SonDTO addSon(String parentId, RegisterSonDTO registerSonDTO) {
        SonEntity sonToAdd = sonEntityMapper.registerSonDTOToSonEntity(registerSonDTO);
        ParentEntity parentEntity = parentRepository.findOne(new ObjectId(parentId));
        sonToAdd.setParent(parentEntity);
        SonEntity sonSaved = sonRepository.save(sonToAdd);
        return sonEntityMapper.sonEntityToSonDTO(sonSaved);
    }

    @Override
    public SonDTO updateSon(String parentId, UpdateSonDTO updateSonDTO) {
        SonEntity sonEntityToUpdate = sonRepository.findOne(new ObjectId(updateSonDTO.getIdentity()));
        sonEntityToUpdate.setFirstName(updateSonDTO.getFirstName());
        sonEntityToUpdate.setLastName(updateSonDTO.getLastName());
        sonEntityToUpdate.setBirthdate(updateSonDTO.getBirthdate());
        sonEntityToUpdate.setSchool(schoolRepository.findOne(new ObjectId(updateSonDTO.getSchool())));
        SonEntity sonEntityUpdated = sonRepository.save(sonEntityToUpdate);
        return sonEntityMapper.sonEntityToSonDTO(sonEntityUpdated);
    }

    @Override
    public ParentDTO getParentById(ObjectId id) {
        ParentEntity parentEntity = parentRepository.findOne(id);
        return parentEntityMapper.parentEntityToParentDTO(parentEntity);
    }

    @Override
    public void setAsNotActiveAndConfirmationToken(String id, String confirmationToken) {
        parentRepository.setAsNotActiveAndConfirmationToken(new ObjectId(id), confirmationToken);
    }

    @Override
    public ParentDTO getParentByEmail(String email) {
        ParentEntity parentEntity = (ParentEntity) parentRepository.findOneByEmail(email);
        return parentEntityMapper.parentEntityToParentDTO(parentEntity);
    }

    @Override
    public ParentDTO update(final ObjectId id, final UpdateParentDTO updateParentDTO) {

        final ParentEntity parentToUpdate = parentRepository.findOne(id);
        // update parent
        parentToUpdate.setFirstName(updateParentDTO.getFirstName());
        parentToUpdate.setLastName(updateParentDTO.getLastName());
        parentToUpdate.setEmail(updateParentDTO.getEmail());
        parentToUpdate.setBirthdate(updateParentDTO.getBirthdate());
        parentToUpdate.setTelephone(PhoneNumberUtil.getInstance().format(updateParentDTO.getTelephone(), PhoneNumberFormat.E164));

        final ParentEntity parentUpdated = parentRepository.save(parentToUpdate);
        return parentEntityMapper.parentEntityToParentDTO(parentUpdated);
    }

    @Override
    public void changeUserPassword(ObjectId id, String newPassword) {
        parentRepository.setNewPassword(id, passwordEncoder.encode(newPassword));
    }

    @Override
    public Boolean activateAccount(String token) {
        Assert.notNull(token, "Token can not be null");
        Assert.hasLength(token, "Token can not be empty");

        Boolean isActivated = Boolean.FALSE;
        Boolean exists = parentRepository.countByConfirmationToken(token) == 1
                ? Boolean.TRUE : Boolean.FALSE;

        if (exists) {
            parentRepository.setActiveAsTrueAndDeleteConfirmationToken(token);
            isActivated = Boolean.TRUE;
        }

        return isActivated;
    }

    @Override
    public void lockAccount(String id) {
        parentRepository.lockAccount(new ObjectId(id));
    }

    @Override
    public void unlockAccount(String id) {
        parentRepository.unlockAccount(new ObjectId(id));
    }

    @Override
    public ParentDTO getParentByFbId(String fbId) {
        return parentEntityMapper.parentEntityToParentDTO(parentRepository.findByFbId(fbId));
    }

    @Override
    public void updateFbAccessToken(String fbId, String fbAccessToken) {
        parentRepository.setFbAccessTokenByFbId(fbAccessToken, fbId);
    }

    @Override
    public Long deleteAccount(String confirmationToken) {
        return parentRepository.deleteByConfirmationToken(confirmationToken);
    }

    @Override
    public void cancelAccountDeletionProcess(String confirmationToken) {
        parentRepository.setPendingDeletionAsFalseAndDeleteConfirmationToken(confirmationToken);
    }

    @Override
    public void startAccountDeletionProcess(ObjectId id, String confirmationToken) {
        parentRepository.setPendingDeletionAsTrueAndConfirmationTokenById(id, confirmationToken);
    }

    @Override
    public Long deleteUnactivatedAccounts() {
        return parentRepository.deleteByActiveFalse();
    }

    @Override
    public void cancelAllAccountDeletionProcess() {
        parentRepository.setPendingDeletionAsFalseAndDeleteConfirmationToken();
    }

    @Override
    public void updateLastAccessToAlerts(ObjectId id) {
        parentRepository.setLastAccessToAlerts(id);
    }

    @Override
    public String getProfileImage(ObjectId id) {
        return parentRepository.getProfileImageIdByUserId(id);
    }
    
    @Override
	public UserSystemPreferencesDTO savePreferences(SaveUserSystemPreferencesDTO preferences, ObjectId idParent) {
    	Assert.notNull(preferences, "Preferences can not be null");
    	
    	ParentEntity parent = parentRepository.findOne(idParent);
    	parent.getPreferences().setPushNotificationsEnabled(preferences.isPushNotificationsEnabled());
    	parentRepository.save(parent);
    	return preferencesEntityMapper.preferencesEntityToUserSystemPreferencesDTO(parent.getPreferences());
    	
	}
    
    @Override
	public UserSystemPreferencesDTO getPreferences(ObjectId idParent) {
    	Assert.notNull(idParent, "Id Parent can not be null");
    	return preferencesEntityMapper.preferencesEntityToUserSystemPreferencesDTO(parentRepository.getPreferences(idParent));
	}

    @PostConstruct
    protected void init() {
        Assert.notNull(parentRepository, "Parent Repository can not be null");
        Assert.notNull(parentEntityMapper, "Parent Entity Mapper can not be null");
        Assert.notNull(sonEntityMapper, "Son Entity Mapper can not be null");
        Assert.notNull(sonRepository, "Son Repository can not be null");
        Assert.notNull(passwordEncoder, "Password Encoder can not be null");
        Assert.notNull(schoolRepository, "School Repository can not be null");
        Assert.notNull(preferencesEntityMapper, "Preferences Entity Mapper can not be null");
        
    }
}
