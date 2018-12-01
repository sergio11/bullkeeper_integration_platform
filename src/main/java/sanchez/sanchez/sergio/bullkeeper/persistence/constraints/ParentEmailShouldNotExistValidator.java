package sanchez.sanchez.sergio.bullkeeper.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.bullkeeper.persistence.repository.GuardianRepository;


public class ParentEmailShouldNotExistValidator implements ConstraintValidator<ParentEmailShouldNotExist, String> {
    
    private static Logger logger = LoggerFactory.getLogger(ParentEmailShouldNotExistValidator.class);
    
    @Autowired
    private GuardianRepository parentRepository;

    @Override
    public void initialize(ParentEmailShouldNotExist constraintAnnotation) {}

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
    	return parentRepository.countByEmail(email) == 0 ? Boolean.TRUE : Boolean.FALSE;
    }
}
