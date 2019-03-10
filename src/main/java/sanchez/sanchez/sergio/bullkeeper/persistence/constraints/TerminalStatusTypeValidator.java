package sanchez.sanchez.sergio.bullkeeper.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.EnumUtils;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.TerminalStatusEnum;

/**
 * Call Detail Type Validator
 * @author sergiosanchezsanchez
 *
 */
public class TerminalStatusTypeValidator implements ConstraintValidator<TerminalStatusType, String> {
    
    @Override
    public void initialize(TerminalStatusType constraintAnnotation) {}

    /**
     * Is Valid
     */
    @Override
    public boolean isValid(String terminalStatusType, ConstraintValidatorContext context) {
    	return terminalStatusType != null &&
    			!terminalStatusType.isEmpty() && EnumUtils.getEnum(TerminalStatusEnum.class, 
    					terminalStatusType) != null;
    }
}
