package sanchez.sanchez.sergio.bullkeeper.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.EnumUtils;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AppRuleEnum;

/**
 * Valid App Rule Type Validator
 * @author sergiosanchezsanchez
 *
 */
public class ValidAppRuleTypeValidator implements ConstraintValidator<ValidAppRuleType, String> {
    
    @Override
    public void initialize(ValidAppRuleType constraintAnnotation) {}

    @Override
    public boolean isValid(String appRuleType, ConstraintValidatorContext context) {
    	return EnumUtils.getEnum(AppRuleEnum.class, appRuleType) != null;
    }
}
