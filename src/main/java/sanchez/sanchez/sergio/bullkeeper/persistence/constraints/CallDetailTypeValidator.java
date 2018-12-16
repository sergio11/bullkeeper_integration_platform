package sanchez.sanchez.sergio.bullkeeper.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.EnumUtils;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.CallTypeEnum;

/**
 * Call Detail Type Validator
 * @author sergiosanchezsanchez
 *
 */
public class CallDetailTypeValidator implements ConstraintValidator<CallDetailType, String> {
    
    @Override
    public void initialize(CallDetailType constraintAnnotation) {}

    /**
     * Is Valid
     */
    @Override
    public boolean isValid(String callDetailType, ConstraintValidatorContext context) {
    	return callDetailType != null &&
    			!callDetailType.isEmpty() && EnumUtils.getEnum(CallTypeEnum.class, 
    					callDetailType) != null;
    }
}
