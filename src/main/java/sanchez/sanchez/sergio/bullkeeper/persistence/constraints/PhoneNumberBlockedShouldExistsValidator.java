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
    public boolean isValid(String idOrPhonenumber, ConstraintValidatorContext context) {
    	return idOrPhonenumber != null 
    				&& !idOrPhonenumber.isEmpty() && (
    						( ObjectId.isValid(idOrPhonenumber) &&  phoneNumberBlockedRepository
    								.countById(new ObjectId(idOrPhonenumber)) > 0 ) || 
    						phoneNumberBlockedRepository.countByPhoneNumber(idOrPhonenumber) > 0);
    }
}
