package es.bisite.usal.bulltect.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.EnumUtils;

import es.bisite.usal.bulltect.persistence.entity.PreferencesEntity.RemoveAlertsEveryEnum;

public class ValidRemoveAlertsEveryValidator implements ConstraintValidator<ValidRemoveAlertsEvery, String> {
    
    @Override
    public void initialize(ValidRemoveAlertsEvery constraintAnnotation) {}

    @Override
    public boolean isValid(String removeAlertEvery, ConstraintValidatorContext context) {
    	return EnumUtils.getEnum(RemoveAlertsEveryEnum.class, removeAlertEvery) != null;
    }
}
