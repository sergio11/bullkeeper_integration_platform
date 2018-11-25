package sanchez.sanchez.sergio.bullkeeper.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.bullkeeper.persistence.repository.ScheduledBlockRepository;

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
    private ScheduledBlockRepository scheduledBlockRepository;
    
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
