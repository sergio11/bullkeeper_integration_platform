package es.bisite.usal.bulltect.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import es.bisite.usal.bulltect.persistence.repository.ParentRepository;


public class ShouldNotBeAFacebookUserValidator implements ConstraintValidator<ShouldNotBeAFacebookUser, String> {
    
    private static Logger logger = LoggerFactory.getLogger(ShouldNotBeAFacebookUserValidator.class);
    
    @Autowired
    private ParentRepository parentRepository;

    @Override
    public void initialize(ShouldNotBeAFacebookUser constraintAnnotation) {}

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        String fbId = parentRepository.getFbIdByEmail(email);
        logger.debug("FB Id -> " + fbId);
        return fbId != null && !fbId.isEmpty() ? Boolean.FALSE : Boolean.TRUE;
    }
}
