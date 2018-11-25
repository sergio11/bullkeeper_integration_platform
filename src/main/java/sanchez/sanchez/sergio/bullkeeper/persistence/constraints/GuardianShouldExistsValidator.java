package sanchez.sanchez.sergio.bullkeeper.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.bullkeeper.persistence.repository.GuardianRepository;

public class GuardianShouldExistsValidator implements ConstraintValidator<GuardianShouldExists, String> {
    
    @Autowired
    private GuardianRepository guardianRepository;
    
    @Override
    public void initialize(GuardianShouldExists constraintAnnotation) {}

    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
    	return guardianRepository.exists(new ObjectId(id));
    }
}
