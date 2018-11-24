package sanchez.sanchez.sergio.masoc.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import sanchez.sanchez.sergio.masoc.persistence.repository.ScheduledBlockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Scheduled Block Name should be unique validator
 * @author sergiosanchezsanchez
 *
 */
public class ScheduledBlockNameShouldBeUniqueValidator 
	implements ConstraintValidator<ScheduledBlockNameShouldBeUnique, String> {
    
    private static Logger logger = LoggerFactory.getLogger(ScheduledBlockNameShouldBeUniqueValidator.class);
    
    /**
     * Scheduled Block Repository
     */
    @Autowired
    private ScheduledBlockRepository scheduledBlockRepository;
    
    /**
     * 
     */
    @Override
    public void initialize(ScheduledBlockNameShouldBeUnique constraintAnnotation) {}

    /**
     * Is valid
     */
    @Override
    public boolean isValid(String scheduledBlockName, ConstraintValidatorContext context) {
    	return scheduledBlockName != null && !scheduledBlockName.isEmpty() &&
    			scheduledBlockRepository.countByName(scheduledBlockName) == 0;
    }
}
