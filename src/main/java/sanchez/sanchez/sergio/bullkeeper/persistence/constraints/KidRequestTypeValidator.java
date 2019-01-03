package sanchez.sanchez.sergio.bullkeeper.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.EnumUtils;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.RequestTypeEnum;

/**
 * Kid Request Type Validator
 * @author sergiosanchezsanchez
 *
 */
public class KidRequestTypeValidator implements ConstraintValidator<KidRequestType, String> {
    
    @Override
    public void initialize(KidRequestType constraintAnnotation) {}

    /**
     * Is Valid
     */
    @Override
    public boolean isValid(String kidRequestType, ConstraintValidatorContext context) {
    	return kidRequestType != null &&
    			!kidRequestType.isEmpty() && EnumUtils.getEnum(RequestTypeEnum.class, 
    					kidRequestType) != null;
    }
}
