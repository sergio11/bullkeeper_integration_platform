package sanchez.sanchez.sergio.bullkeeper.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.GeofenceRepository;

/**
 * Geofence Should Exists Validator
 * @author sergiosanchezsanchez
 *
 */
public class GeofenceShouldExistsValidator implements ConstraintValidator<GeofenceShouldExists, String> {
	
	private static Logger logger = LoggerFactory.getLogger(GeofenceShouldExistsValidator.class);
    
	/**
	 * Geofence Repository
	 */
    @Autowired
    private GeofenceRepository geofenceRepository;
    
    /**
     * 
     */
    @Override
    public void initialize(GeofenceShouldExists constraintAnnotation) {}

    /**
     * 
     */
    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
    	return id != null && !id.isEmpty() && ObjectId.isValid(id) && 
    			geofenceRepository.countById(new ObjectId(id)) > 0 ? Boolean.TRUE : Boolean.FALSE;
    }
}
