package sanchez.sanchez.sergio.bullkeeper.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.EnumUtils;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SmsFolderNameEnum;

/**
 * Sms Folder Name Validator
 * @author sergiosanchezsanchez
 *
 */
public class SmsFolderNameValidator implements ConstraintValidator<SmsFolderNameType, String> {
    
    @Override
    public void initialize(SmsFolderNameType constraintAnnotation) {}

    /**
     * 
     */
    @Override
    public boolean isValid(String smsFolderNameType, ConstraintValidatorContext context) {
    	return smsFolderNameType != null &&
    			!smsFolderNameType.isEmpty() && EnumUtils.getEnum(SmsFolderNameEnum.class, 
    					smsFolderNameType) != null;
    }
}
