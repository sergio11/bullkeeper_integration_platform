package sanchez.sanchez.sergio.bullkeeper.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.EnumUtils;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.RequestTypeEnum;

/**
 * Request Type Validator
 * @author sergiosanchezsanchez
 *
 */
public class RequestTypeValidator implements ConstraintValidator<RequestType, String> {
    
	/**
	 * 
	 */
    @Override
    public void initialize(RequestType constraintAnnotation) {}

    /**
     * Is Valid
     */
    @Override
    public boolean isValid(String requestType, ConstraintValidatorContext context) {
    	return requestType != null &&
    			!requestType.isEmpty() && EnumUtils.getEnum(RequestTypeEnum.class, 
    					requestType) != null;
    }
}
