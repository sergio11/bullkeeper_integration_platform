package sanchez.sanchez.sergio.bullkeeper.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.bullkeeper.persistence.repository.KidRequestRepository;

/**
 * Kid Request Should Exists Validator
 * @author sergiosanchezsanchez
 *
 */
public class KidRequestShouldExistsValidator implements ConstraintValidator<KidRequestShouldExists, String> {
    
	/**
	 * Kid Request Repository
	 */
    @Autowired
    private KidRequestRepository kidRequestRepository;
    
   
    /**
     * 
     * @param constraintAnnotation
     */
    @Override
    public void initialize(KidRequestShouldExists constraintAnnotation) {}

    /**
     * 
     */
    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
    	return id != null && !id.isEmpty() 
    			&& ObjectId.isValid(id) && kidRequestRepository.countById(new ObjectId(id)) > 0;
    }
}
