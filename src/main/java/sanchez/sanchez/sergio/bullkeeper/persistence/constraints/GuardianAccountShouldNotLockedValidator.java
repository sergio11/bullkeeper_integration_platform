package sanchez.sanchez.sergio.bullkeeper.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.bullkeeper.persistence.repository.GuardianRepository;


public class GuardianAccountShouldNotLockedValidator implements ConstraintValidator<GuardianAccountShouldNotLocked, String> {
    
    private static Logger logger = LoggerFactory.getLogger(GuardianAccountShouldNotLockedValidator.class);
    
    @Autowired
    private GuardianRepository parentRepository;

    @Override
    public void initialize(GuardianAccountShouldNotLocked constraintAnnotation) {}

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
    	return parentRepository.countByEmailAndLockedFalse(email) > 0 ? Boolean.TRUE : Boolean.FALSE;
    }
}
