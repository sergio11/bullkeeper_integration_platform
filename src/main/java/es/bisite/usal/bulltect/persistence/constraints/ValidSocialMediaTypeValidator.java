package es.bisite.usal.bulltect.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.EnumUtils;

import es.bisite.usal.bulltect.persistence.entity.SocialMediaTypeEnum;

public class ValidSocialMediaTypeValidator implements ConstraintValidator<ValidSocialMediaType, String> {
    
    @Override
    public void initialize(ValidSocialMediaType constraintAnnotation) {}

    @Override
    public boolean isValid(String socialMediaType, ConstraintValidatorContext context) {
    	return EnumUtils.getEnum(SocialMediaTypeEnum.class, socialMediaType) != null;
    }
}
