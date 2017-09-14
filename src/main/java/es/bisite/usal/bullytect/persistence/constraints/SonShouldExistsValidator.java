package es.bisite.usal.bullytect.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import es.bisite.usal.bullytect.persistence.repository.SonRepository;

public class SonShouldExistsValidator implements ConstraintValidator<SonShouldExists, String> {
    
    @Autowired
    private SonRepository sonRepository;
    
    @Override
    public void initialize(SonShouldExists constraintAnnotation) {}

    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
    	return sonRepository.countById(new ObjectId(id)) > 0 ? Boolean.TRUE : Boolean.FALSE;
    }
}
