package sanchez.sanchez.sergio.masoc.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.masoc.persistence.repository.ImageRepository;
import sanchez.sanchez.sergio.masoc.persistence.repository.ParentRepository;

/**
 * Image Should Exists
 * @author sergiosanchezsanchez
 *
 */
public class ImageShouldExistsValidator implements 
	ConstraintValidator<ImageShouldExists, String> {
    
	/**
	 * Image Repository
	 */
    @Autowired
    private ImageRepository imageRepository;
    
    @Override
    public void initialize(ImageShouldExists constraintAnnotation) {}

    /**
     * Is Valid
     */
    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
    	return imageRepository.countById(new ObjectId(id)) > 0;
    }
}
