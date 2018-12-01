package sanchez.sanchez.sergio.bullkeeper.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.bson.types.ObjectId;

public class ValidObjectIdValidator implements ConstraintValidator<ValidObjectId, String> {
    
    @Override
    public void initialize(ValidObjectId constraintAnnotation) {}

    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
    	return ObjectId.isValid(id);
    }
}
