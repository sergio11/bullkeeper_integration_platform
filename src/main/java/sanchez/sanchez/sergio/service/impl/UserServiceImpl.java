package sanchez.sanchez.sergio.service.impl;

import org.bson.types.ObjectId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import sanchez.sanchez.sergio.dto.response.UserDTO;
import sanchez.sanchez.sergio.mapper.IUserEntityMapper;
import sanchez.sanchez.sergio.persistence.entity.UserEntity;
import sanchez.sanchez.sergio.persistence.repository.UserRepository;
import sanchez.sanchez.sergio.service.IUserService;

/**
 *
 * @author sergio
 */
@Service("userService")
public class UserServiceImpl implements IUserService {
    
    private final UserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;

    public UserServiceImpl(UserRepository userRepository, IUserEntityMapper userEntityMapper) {
        this.userRepository = userRepository;
        this.userEntityMapper = userEntityMapper;
    }
    
    @Override
    public Long getTotalUsers() {
        return userRepository.count();
    }

    @Override
    public Page<UserDTO> findPaginated(Integer page, Integer size) {
        Pageable pageable = new PageRequest(page, size);
        Page<UserEntity> usersPage = userRepository.findAll(pageable);
        return usersPage.map(new Converter<UserEntity, UserDTO>(){
            @Override
            public UserDTO convert(UserEntity u) {
               return userEntityMapper.userEntityToUserDTO(u);
            }
        });
    }

    @Override
    public Page<UserDTO> findPaginated(Pageable pageable) {
        Page<UserEntity> usersPage = userRepository.findAll(pageable);
        return usersPage.map(new Converter<UserEntity, UserDTO>(){
            @Override
            public UserDTO convert(UserEntity u) {
               return userEntityMapper.userEntityToUserDTO(u);
            }
        });
    }

    @Override
    public UserDTO getUserById(String id) {
        UserEntity userEntity = userRepository.findOne(new ObjectId(id));
        return userEntityMapper.userEntityToUserDTO(userEntity);
    }
    
}
