package sanchez.sanchez.sergio.bullkeeper.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.EnumUtils;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.FunTimeDaysEnum;

/**
 * Day Name Type Validator
 * @author sergiosanchezsanchez
 */
public class DayNameTypeValidator implements ConstraintValidator<DayNameValidator, String> {
    
	/**
	 * Day
	 */
    @Override
    public void initialize(DayNameValidator constraintAnnotation) {}

    /**
     * Is Valid
     */
    @Override
    public boolean isValid(String dayName, ConstraintValidatorContext context) {
    	return dayName != null &&
    			!dayName.isEmpty() && EnumUtils.getEnum(FunTimeDaysEnum.class, 
    					dayName) != null;
    }
}
