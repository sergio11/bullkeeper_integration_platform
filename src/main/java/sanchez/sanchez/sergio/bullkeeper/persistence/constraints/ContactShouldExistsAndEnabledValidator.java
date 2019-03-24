package sanchez.sanchez.sergio.bullkeeper.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.ContactEntityRepository;


/**
 * Contact Should Exists Validator
 * @author sergiosanchezsanchez
 *
 */
public class ContactShouldExistsAndEnabledValidator implements ConstraintValidator<ContactShouldExistsAndEnabled, String> {
    
	/**
	 * Contact Entity Repository
	 */
    @Autowired
    private ContactEntityRepository contactEntityRepository;
    
    /**
     * @param constraintAnnotation
     */
    @Override
    public void initialize(ContactShouldExistsAndEnabled constraintAnnotation) {}

    /**
     * 
     */
    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
    	return id != null && !id.isEmpty() 
    			&& ObjectId.isValid(id) && contactEntityRepository.countByIdAndDisabledFalse(new ObjectId(id)) > 0;
    }
}
