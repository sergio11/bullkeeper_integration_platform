package sanchez.sanchez.sergio.bullkeeper.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.bullkeeper.persistence.repository.ScheduledBlockRepository;

/**
 * Scheduled Block Validator
 * @author sergiosanchezsanchez
 *
 */
public class IsWeeklyFrequencyValidValidator implements ConstraintValidator<IsWeeklyFrequencyValid, int[]> {
    
	/**
	 * Scheduled Block Repository
	 */
    @Autowired
    private ScheduledBlockRepository scheduledBlockRepository;
    
    @Override
    public void initialize(IsWeeklyFrequencyValid constraintAnnotation) {}

    /**
     * 
     */
    @Override
    public boolean isValid(int[] weeklyFrequency, ConstraintValidatorContext context) {
    	
    	boolean isValid = true;
    	
    	if(weeklyFrequency != null && weeklyFrequency.length == 7 ) {
    		for(final int dayOfWeek: weeklyFrequency) {
    			if(dayOfWeek != 0 && dayOfWeek != 1) {
    				isValid = false;
    				break;
    			}
    		}
    	} else {
    		isValid = false;
    	}
    	
    	return isValid;
    }
}
