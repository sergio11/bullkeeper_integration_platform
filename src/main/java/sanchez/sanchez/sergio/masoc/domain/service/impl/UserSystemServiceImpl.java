package sanchez.sanchez.sergio.masoc.domain.service.impl;

import javax.annotation.PostConstruct;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.lang.Assert;
import sanchez.sanchez.sergio.masoc.domain.service.IUserSystemService;
import sanchez.sanchez.sergio.masoc.mapper.UserSystemEntityMapper;
import sanchez.sanchez.sergio.masoc.persistence.entity.UserSystemEntity;
import sanchez.sanchez.sergio.masoc.persistence.repository.UserSystemRepository;
import sanchez.sanchez.sergio.masoc.web.dto.response.AdminDTO;

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
