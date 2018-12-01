package sanchez.sanchez.sergio.bullkeeper.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.bullkeeper.persistence.repository.ScheduledBlockRepository;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Scheduled Block Should Exists if present validator
 * @author sergiosanchezsanchez
 *
 */
public class ScheduledBlockShouldExistsIfPresentValidator implements ConstraintValidator<ScheduledBlockShouldExistsIfPresent, String> {
    
    private static Logger logger = LoggerFactory.getLogger(ScheduledBlockShouldExistsIfPresentValidator.class);
    
    /**
     * Scheduled Block Repository
     */
    @Autowired
    private ScheduledBlockRepository scheduledBlockRepository;
    
    /**
     * 
     */
    @Override
    public void initialize(ScheduledBlockShouldExistsIfPresent constraintAnnotation) {}

    /**
     * Is valid
     */
    @Override
    public boolean isValid(String scheduledBlockId, ConstraintValidatorContext context) {
    	return ( scheduledBlockId == null || scheduledBlockId.isEmpty() ) || 
    			( ObjectId.isValid(scheduledBlockId) && scheduledBlockRepository.countById(new ObjectId(scheduledBlockId)) > 0 
    						? Boolean.TRUE : Boolean.FALSE);
    }
}
