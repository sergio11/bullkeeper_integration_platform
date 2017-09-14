package es.bisite.usal.bullytect.service.impl;

import javax.annotation.PostConstruct;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import es.bisite.usal.bullytect.dto.response.AdminDTO;
import es.bisite.usal.bullytect.mapper.UserSystemEntityMapper;
import es.bisite.usal.bullytect.persistence.entity.UserSystemEntity;
import es.bisite.usal.bullytect.persistence.repository.UserSystemRepository;
import es.bisite.usal.bullytect.service.IUserSystemService;
import io.jsonwebtoken.lang.Assert;

@Service
public class UserSystemServiceImpl implements IUserSystemService {
	
	
	private final UserSystemRepository userSystemRepository;
	private final UserSystemEntityMapper userSystemEntityMapper;
	


	public UserSystemServiceImpl(UserSystemRepository userSystemRepository,
			UserSystemEntityMapper userSystemEntityMapper) {
		super();
		this.userSystemRepository = userSystemRepository;
		this.userSystemEntityMapper = userSystemEntityMapper;
	}


	@Override
	public AdminDTO getUserById(String id) {
		UserSystemEntity userSystemEntity = userSystemRepository.findOne(new ObjectId(id));
		return userSystemEntityMapper.userSystemEntityToAdminDTO(userSystemEntity);
	}
	
	@Override
	public AdminDTO getUserById(ObjectId id) {
		UserSystemEntity userSystemEntity = userSystemRepository.findOne(id);
		return userSystemEntityMapper.userSystemEntityToAdminDTO(userSystemEntity);
	}
	
	
	@PostConstruct
	protected void init(){
		Assert.notNull(userSystemRepository, "User System Repository can not be null");
		Assert.notNull(userSystemEntityMapper, "User System Entity Mapper can not be null");
	}

}
