package sanchez.sanchez.sergio.bullkeeper.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.EnumUtils;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AnalysisStatusEnum;

public class ValidAlertLevelValidator implements ConstraintValidator<ValidAlertLevel, String> {
    
    @Override
    public void initialize(ValidAlertLevel constraintAnnotation) {}

    @Override
    public boolean isValid(String alertLevel, ConstraintValidatorContext context) {
    	return EnumUtils.getEnum(AnalysisStatusEnum.class, alertLevel) != null;
    }
}
