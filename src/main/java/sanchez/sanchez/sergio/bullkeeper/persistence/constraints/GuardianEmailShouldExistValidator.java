package sanchez.sanchez.sergio.bullkeeper.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.bullkeeper.persistence.repository.GuardianRepository;


public class GuardianEmailShouldExistValidator implements ConstraintValidator<GuardianEmailShouldExist, String> {
    
    private static Logger logger = LoggerFactory.getLogger(GuardianEmailShouldExistValidator.class);
    
    @Autowired
    private GuardianRepository guardianRepository;

    @Override
    public void initialize(GuardianEmailShouldExist constraintAnnotation) {}

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
    	return guardianRepository.countByEmail(email) > 0 ? Boolean.TRUE : Boolean.FALSE;
    }
}
