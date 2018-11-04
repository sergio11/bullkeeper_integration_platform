package sanchez.sanchez.sergio.masoc.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.EnumUtils;

import sanchez.sanchez.sergio.masoc.persistence.entity.AppRuleEnum;
import sanchez.sanchez.sergio.masoc.persistence.entity.SocialMediaTypeEnum;

/**
 * Valid App Rule Type Validator
 * @author sergiosanchezsanchez
 *
 */
public class ValidAppRuleTypeValidator implements ConstraintValidator<ValidSocialMediaType, String> {
    
    @Override
    public void initialize(ValidSocialMediaType constraintAnnotation) {}

    @Override
    public boolean isValid(String appRuleType, ConstraintValidatorContext context) {
    	return EnumUtils.getEnum(AppRuleEnum.class, appRuleType) != null;
    }
}
