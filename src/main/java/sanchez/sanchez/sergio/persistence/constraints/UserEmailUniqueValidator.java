package sanchez.sanchez.sergio.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import sanchez.sanchez.sergio.persistence.repository.UserSystemRepository;


public class UserEmailUniqueValidator implements ConstraintValidator<UserEmailUnique, String> {
    
    private static Logger logger = LoggerFactory.getLogger(UserEmailUniqueValidator.class);
    
    @Autowired
    private UserSystemRepository userSystemRepository;

    @Override
    public void initialize(UserEmailUnique constraintAnnotation) {}

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
    	return userSystemRepository.countByEmail(email) > 0 ? Boolean.TRUE : Boolean.FALSE;
    }
}
