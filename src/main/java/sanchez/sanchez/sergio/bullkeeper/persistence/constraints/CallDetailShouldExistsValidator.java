package sanchez.sanchez.sergio.bullkeeper.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.bullkeeper.persistence.repository.CallDetailRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.SmsRepository;

/**
 * Call Detail Should Exists Validator
 * @author sergiosanchezsanchez
 *
 */
public class CallDetailShouldExistsValidator implements ConstraintValidator<CallDetailShouldExists, String> {
    
	/**
	 * Call Detail Repository
	 */
    @Autowired
    private CallDetailRepository callDetailRepository;
    
   
    /**
     * 
     * @param constraintAnnotation
     */
    @Override
    public void initialize(CallDetailShouldExists constraintAnnotation) {}

    /**
     * 
     */
    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
    	return id != null && !id.isEmpty() 
    			&& ObjectId.isValid(id) && callDetailRepository.countById(new ObjectId(id)) > 0;
    }
}
