package sanchez.sanchez.sergio.bullkeeper.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.EnumUtils;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.ScreenStatusEnum;

/**
 * Screen Status Type Validator
 * @author sergiosanchezsanchez
 *
 */
public class ScreenStatusTypeValidator implements ConstraintValidator<ScreenStatusType, String> {
    
    @Override
    public void initialize(ScreenStatusType constraintAnnotation) {}

    /**
     * Is Valid
     */
    @Override
    public boolean isValid(String screenStatusType, ConstraintValidatorContext context) {
    	return screenStatusType != null &&
    			!screenStatusType.isEmpty() && EnumUtils.getEnum(ScreenStatusEnum.class, 
    					screenStatusType) != null;
    }
}
