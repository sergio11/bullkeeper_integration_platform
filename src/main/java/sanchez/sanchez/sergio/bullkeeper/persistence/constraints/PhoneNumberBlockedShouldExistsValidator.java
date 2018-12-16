package sanchez.sanchez.sergio.bullkeeper.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.PhoneNumberBlockedRepository;

/**
 * 
 * @author sergiosanchezsanchez
 *
 */
public class PhoneNumberBlockedShouldExistsValidator implements ConstraintValidator<PhoneNumberBlockedShouldExists, String> {
	
	private static Logger logger = LoggerFactory.getLogger(PhoneNumberBlockedShouldExistsValidator.class);
    
	/**
	 * Phone Number Blocked Repository
	 */
    @Autowired
    private PhoneNumberBlockedRepository phoneNumberBlockedRepository;
    
    /**
     * 
     */
    @Override
    public void initialize(PhoneNumberBlockedShouldExists constraintAnnotation) {}

    /**
     * 
     */
    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
    	return id == null || (id != null && id.isEmpty()) || !ObjectId.isValid(id) || 
    			phoneNumberBlockedRepository.countById(new ObjectId(id)) > 0 ? Boolean.TRUE : Boolean.FALSE;
    }
}
