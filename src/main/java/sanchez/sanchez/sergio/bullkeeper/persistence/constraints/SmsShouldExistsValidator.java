package sanchez.sanchez.sergio.bullkeeper.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.bullkeeper.persistence.repository.SmsRepository;

/**
 * Sms Should Exists Validator
 * @author sergiosanchezsanchez
 *
 */
public class SmsShouldExistsValidator implements ConstraintValidator<SmsShouldExists, String> {
    
	/**
	 * Sms Repository
	 */
    @Autowired
    private SmsRepository smsRepository;
    
    
    /**
     * 
     * @param constraintAnnotation
     */
    @Override
    public void initialize(SmsShouldExists constraintAnnotation) {}

    /**
     * 
     */
    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
    	return id != null && !id.isEmpty() 
    			&& ObjectId.isValid(id) && smsRepository.countById(new ObjectId(id)) > 0;
    }
}
