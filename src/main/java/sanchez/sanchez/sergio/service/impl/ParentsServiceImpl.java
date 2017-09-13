package sanchez.sanchez.sergio.service.impl;


import javax.annotation.PostConstruct;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.lang.Assert;
import sanchez.sanchez.sergio.dto.request.RegisterParentByFacebookDTO;
import sanchez.sanchez.sergio.dto.request.RegisterParentDTO;
import sanchez.sanchez.sergio.dto.request.RegisterSonDTO;
import sanchez.sanchez.sergio.dto.request.UpdateParentDTO;
import sanchez.sanchez.sergio.dto.response.ParentDTO;
import sanchez.sanchez.sergio.dto.response.SonDTO;
import sanchez.sanchez.sergio.mapper.ParentEntityMapper;
import sanchez.sanchez.sergio.mapper.SonEntityMapper;
import sanchez.sanchez.sergio.persistence.entity.ParentEntity;
import sanchez.sanchez.sergio.persistence.entity.SonEntity;
import sanchez.sanchez.sergio.persistence.repository.ParentRepository;
import sanchez.sanchez.sergio.persistence.repository.SonRepository;
import sanchez.sanchez.sergio.service.IParentsService;

@Service
public class ParentsServiceImpl implements IParentsService {

    private final ParentRepository parentRepository;
    private final ParentEntityMapper parentEntityMapper;
    private final SonEntityMapper sonEntityMapper;
    private final SonRepository sonRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ParentsServiceImpl(ParentRepository parentRepository, ParentEntityMapper parentEntityMapper, SonEntityMapper sonEntityMapper, 
    		SonRepository sonRepository, PasswordEncoder passwordEncoder) {
        super();
        this.parentRepository = parentRepository;
        this.parentEntityMapper = parentEntityMapper;
        this.sonEntityMapper = sonEntityMapper;
        this.sonRepository = sonRepository;
        this.passwordEncoder = passwordEncoder;
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
        final ParentEntity parentSaved = parentRepository.save(parentToSave);
        return parentEntityMapper.parentEntityToParentDTO(parentSaved);
    }
    
    @Override
	public ParentDTO save(RegisterParentByFacebookDTO registerParent) {
    	final ParentEntity parentToSave = parentEntityMapper.registerParentByFacebookDTOToParentEntity(registerParent);
        final ParentEntity parentSaved = parentRepository.save(parentToSave);
        return parentEntityMapper.parentEntityToParentDTO(parentSaved);
	}

    @Override
    public SonDTO addSon(String parentId, RegisterSonDTO registerSonDTO) {
        SonEntity sonToAdd = sonEntityMapper.registerSonDTOToSonEntity(registerSonDTO);
        ParentEntity parentEntity = parentRepository.findOne(new ObjectId(parentId));
        sonToAdd.setParent(parentEntity);
        parentRepository.save(parentEntity);
        return sonEntityMapper.sonEntityToSonDTO(sonToAdd);
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
		ParentEntity parentEntity = (ParentEntity)parentRepository.findOneByEmail(email);
		return parentEntityMapper.parentEntityToParentDTO(parentEntity);
	}

	@Override
	public ParentDTO update(final ObjectId id, final UpdateParentDTO updateParentDTO) {
		
		final ParentEntity parentToUpdate =  parentRepository.findOne(id);
		// update parent
		parentToUpdate.setFirstName(updateParentDTO.getFirstName());
		parentToUpdate.setLastName(updateParentDTO.getLastName());
        final ParentEntity parentUpdated = parentRepository.save(parentToUpdate);
        return parentEntityMapper.parentEntityToParentDTO(parentUpdated);
	}

	@Override
	public void changeUserPassword(ObjectId id, String newPassword) {
		parentRepository.setNewPassword(id, newPassword);
	}
	
	@Override
	public Boolean activateAccount(String token) {
		Assert.notNull(token, "Token can not be null");
		Assert.hasLength(token, "Token can not be empty");
		
		Boolean isActivated = Boolean.FALSE;
		
		Boolean exists = parentRepository.countByConfirmationToken(token) == 1 
				? Boolean.TRUE : Boolean.FALSE;
		
		if(exists) {
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
	
	@PostConstruct
	protected void init(){
		Assert.notNull(parentRepository, "Parent Repository can not be null");
		Assert.notNull(parentEntityMapper, "Parent Entity Mapper can not be null");
		Assert.notNull(sonEntityMapper, "Son Entity Mapper can not be null");
		Assert.notNull(sonRepository, "Son Repository can not be null");
		Assert.notNull(passwordEncoder, "Password Encoder can not be null");
		
	}

	
}
