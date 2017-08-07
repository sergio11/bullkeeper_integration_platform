package sanchez.sanchez.sergio.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.persistence.repository.ParentRepository;


public class ParentUniqueValidator implements ConstraintValidator<ParentEmailUnique, String> {
    
    private static Logger logger = LoggerFactory.getLogger(ParentUniqueValidator.class);
    
    @Autowired
    private ParentRepository parentRepository;

    @Override
    public void initialize(ParentEmailUnique constraintAnnotation) {}

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
    	return true;
    }
}
