package sanchez.sanchez.sergio.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import sanchez.sanchez.sergio.persistence.repository.ParentRepository;

public class ParentShouldExistsValidator implements ConstraintValidator<ParentShouldExists, String> {
    
    @Autowired
    private ParentRepository parentRepository;
    
    @Override
    public void initialize(ParentShouldExists constraintAnnotation) {}

    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
    	return parentRepository.exists(new ObjectId(id));
    }
}
