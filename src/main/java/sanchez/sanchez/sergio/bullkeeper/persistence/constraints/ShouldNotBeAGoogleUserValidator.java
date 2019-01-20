package sanchez.sanchez.sergio.bullkeeper.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.bullkeeper.persistence.repository.GuardianRepository;


public class ShouldNotBeAGoogleUserValidator implements ConstraintValidator<ShouldNotBeAGoogleUser, String> {
    
    private static Logger logger = LoggerFactory.getLogger(ShouldNotBeAGoogleUserValidator.class);
    
    @Autowired
    private GuardianRepository parentRepository;

    @Override
    public void initialize(ShouldNotBeAGoogleUser constraintAnnotation) {}

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        String googleId = parentRepository.getGoogleIdByEmail(email);
        logger.debug("Google Id -> " + googleId);
        return googleId != null && !googleId.isEmpty() ? Boolean.FALSE : Boolean.TRUE;
    }
}
