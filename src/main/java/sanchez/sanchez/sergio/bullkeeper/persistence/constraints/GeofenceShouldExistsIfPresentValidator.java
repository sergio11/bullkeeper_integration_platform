package sanchez.sanchez.sergio.bullkeeper.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.GeofenceRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Geofence Should Exists If Present Validator
 * @author sergiosanchezsanchez
 *
 */
public class GeofenceShouldExistsIfPresentValidator implements ConstraintValidator<GeofenceShouldExistsIfPresent, String> {
    
    private static Logger logger = LoggerFactory.getLogger(GeofenceShouldExistsIfPresentValidator.class);
    
    /**
     * Geofence Repository
     */
    @Autowired
    private GeofenceRepository geofenceRepository;
    
    /**
     * 
     */
    @Override
    public void initialize(GeofenceShouldExistsIfPresent constraintAnnotation) {}

    /**
     * Is valid
     */
    @Override
    public boolean isValid(String geofenceId, ConstraintValidatorContext context) {
    	return ( geofenceId == null || geofenceId.isEmpty() ) || 
    			( ObjectId.isValid(geofenceId) && geofenceRepository.countById(new ObjectId(geofenceId)) > 0 
    						? Boolean.TRUE : Boolean.FALSE);
    }
}
