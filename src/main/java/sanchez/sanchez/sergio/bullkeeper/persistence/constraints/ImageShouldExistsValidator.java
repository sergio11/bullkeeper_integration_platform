package sanchez.sanchez.sergio.bullkeeper.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.bullkeeper.persistence.repository.GuardianRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.ImageRepository;

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
