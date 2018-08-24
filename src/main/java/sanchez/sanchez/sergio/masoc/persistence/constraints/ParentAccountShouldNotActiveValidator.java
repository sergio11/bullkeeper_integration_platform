package sanchez.sanchez.sergio.masoc.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.masoc.persistence.repository.ParentRepository;


public class ParentAccountShouldNotActiveValidator implements ConstraintValidator<ParentAccountShouldNotActive, String> {
    
    private static Logger logger = LoggerFactory.getLogger(ParentAccountShouldNotActiveValidator.class);
    
    @Autowired
    private ParentRepository parentRepository;

    @Override
    public void initialize(ParentAccountShouldNotActive constraintAnnotation) {}

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
    	return parentRepository.countByEmailAndActiveTrue(email) > 0 ? Boolean.FALSE : Boolean.TRUE;
    }
}
