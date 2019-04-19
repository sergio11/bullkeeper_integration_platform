package sanchez.sanchez.sergio.bullkeeper.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.bullkeeper.persistence.repository.GuardianRepository;


public class ParentAccountShouldNotPendingDeleteValidator implements ConstraintValidator<ParentAccountShouldNotPendingDelete, String> {
    
    private static Logger logger = LoggerFactory.getLogger(ParentAccountShouldNotPendingDeleteValidator.class);
    
    @Autowired
    private GuardianRepository parentRepository;

    @Override
    public void initialize(ParentAccountShouldNotPendingDelete constraintAnnotation) {}

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
    	return parentRepository.countByEmailAndPendingDeletionFalse(email) > 0 ? Boolean.TRUE : Boolean.FALSE;
    }
}
