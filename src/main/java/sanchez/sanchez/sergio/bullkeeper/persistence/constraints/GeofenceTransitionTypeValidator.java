package sanchez.sanchez.sergio.bullkeeper.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.EnumUtils;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.GeofenceTransitionTypeEnum;

/**
 * Geofence Tranition Type Validator
 * @author sergiosanchezsanchez
 *
 */
public class GeofenceTransitionTypeValidator implements ConstraintValidator<GeofenceTransitionType, String> {
    
	/**
	 * 
	 */
    @Override
    public void initialize(GeofenceTransitionType constraintAnnotation) {}

    /**
     * Is Valid
     */
    @Override
    public boolean isValid(String geofenceTransitionType, ConstraintValidatorContext context) {
    	return geofenceTransitionType != null &&
    			!geofenceTransitionType.isEmpty() && EnumUtils.getEnum(GeofenceTransitionTypeEnum.class, 
    					geofenceTransitionType) != null;
    }
}
