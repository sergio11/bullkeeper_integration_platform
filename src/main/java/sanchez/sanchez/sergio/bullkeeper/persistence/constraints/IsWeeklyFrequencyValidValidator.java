package sanchez.sanchez.sergio.bullkeeper.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Scheduled Block Validator
 * @author sergiosanchezsanchez
 *
 */
public class IsWeeklyFrequencyValidValidator implements ConstraintValidator<IsWeeklyFrequencyValid, int[]> {
    

    
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
    		
    		boolean atLeastOneDaySet = false;
    		
    		for(final int dayOfWeek: weeklyFrequency) {
    			if(dayOfWeek == 1) {
    				atLeastOneDaySet = true;
    				break;
    			}
    		}
    		
    		
    		if(!atLeastOneDaySet) 
    			isValid = false;
    		
    		
    	} else {
    		isValid = false;
    	}
    	
    	
    	
    	
    	return isValid;
    }
}
