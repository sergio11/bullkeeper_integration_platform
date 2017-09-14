package es.bisite.usal.bullytect.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import es.bisite.usal.bullytect.persistence.repository.SocialMediaRepository;

public class SocialMediaShouldExistsValidator implements ConstraintValidator<SocialMediaShouldExists, String> {
    
    @Autowired
    private SocialMediaRepository socialMediaRepository;
    
    @Override
    public void initialize(SocialMediaShouldExists constraintAnnotation) {}

    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
       return ObjectId.isValid(id) && socialMediaRepository.countById(new ObjectId(id)) > 0 
               ? Boolean.TRUE : Boolean.FALSE;
    }
}
