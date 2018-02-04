package es.bisite.usal.bulltect.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import es.bisite.usal.bulltect.persistence.repository.ParentRepository;


public class ParentEmailShouldExistValidator implements ConstraintValidator<ParentEmailShouldExist, String> {
    
    private static Logger logger = LoggerFactory.getLogger(ParentEmailShouldExistValidator.class);
    
    @Autowired
    private ParentRepository parentRepository;

    @Override
    public void initialize(ParentEmailShouldExist constraintAnnotation) {}

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
    	return parentRepository.countByEmail(email) > 0 ? Boolean.TRUE : Boolean.FALSE;
    }
}
