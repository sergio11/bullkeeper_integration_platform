package sanchez.sanchez.sergio.masoc.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.masoc.persistence.repository.IScheduledBlockRepository;

/**
 * Scheduled Block Validator
 * @author sergiosanchezsanchez
 *
 */
public class ScheduledBlockShouldExistsValidator implements ConstraintValidator<ScheduledBlockShouldExists, String> {
    
	/**
	 * Scheduled Block Repository
	 */
    @Autowired
    private IScheduledBlockRepository scheduledBlockRepository;
    
    @Override
    public void initialize(ScheduledBlockShouldExists constraintAnnotation) {}

    /**
     * 
     */
    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
    	return !ObjectId.isValid(id) || scheduledBlockRepository.countById(new ObjectId(id)) > 0 ? Boolean.TRUE : Boolean.FALSE;
    }
}
