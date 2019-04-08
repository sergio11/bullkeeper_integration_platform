package sanchez.sanchez.sergio.bullkeeper.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.EnumUtils;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.DeviceStatusEnum;

/**
 * Terminal Device Status Type Validator
 * @author sergiosanchezsanchez
 *
 */
public class TerminalDeviceStatusTypeValidator implements ConstraintValidator<TerminalDeviceStatusType, String> {
    
    @Override
    public void initialize(TerminalDeviceStatusType constraintAnnotation) {}

    /**
     * Is Valid
     */
    @Override
    public boolean isValid(String terminalDeviceStatusType, ConstraintValidatorContext context) {
    	return terminalDeviceStatusType != null &&
    			!terminalDeviceStatusType.isEmpty() && EnumUtils.getEnum(DeviceStatusEnum.class, 
    					terminalDeviceStatusType) != null;
    }
}
