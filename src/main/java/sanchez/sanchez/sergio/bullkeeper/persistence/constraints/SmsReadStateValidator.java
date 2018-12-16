package sanchez.sanchez.sergio.bullkeeper.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.EnumUtils;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SmsReadStateEnum;

/**
 * Sms Read State Validator
 * @author sergiosanchezsanchez
 *
 */
public class SmsReadStateValidator implements ConstraintValidator<SmsReadStateType, String> {
    
    @Override
    public void initialize(SmsReadStateType constraintAnnotation) {}

    /**
     * 
     */
    @Override
    public boolean isValid(String smsReadStateType, ConstraintValidatorContext context) {
    	return smsReadStateType != null &&
    			!smsReadStateType.isEmpty() && EnumUtils.getEnum(SmsReadStateEnum.class, smsReadStateType) != null;
    }
}
