package es.bisite.usal.bulltect.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.EnumUtils;

import es.bisite.usal.bulltect.persistence.entity.AlertLevelEnum;

public class ValidAlertLevelValidator implements ConstraintValidator<ValidAlertLevel, String> {
    
    @Override
    public void initialize(ValidAlertLevel constraintAnnotation) {}

    @Override
    public boolean isValid(String alertLevel, ConstraintValidatorContext context) {
    	return EnumUtils.getEnum(AlertLevelEnum.class, alertLevel) != null;
    }
}
