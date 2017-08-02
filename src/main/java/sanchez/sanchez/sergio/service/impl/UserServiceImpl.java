package sanchez.sanchez.sergio.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sanchez.sanchez.sergio.persistence.repository.UserRepository;
import sanchez.sanchez.sergio.service.IUserService;

/**
 *
 * @author sergio
 */
@Service("userService")
public class UserServiceImpl implements IUserService {
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public Long getTotalUsers() {
        return userRepository.count();
    }
    
}
