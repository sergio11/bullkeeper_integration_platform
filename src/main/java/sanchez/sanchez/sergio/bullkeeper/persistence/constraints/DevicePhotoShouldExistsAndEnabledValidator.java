package sanchez.sanchez.sergio.bullkeeper.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.bullkeeper.persistence.repository.DevicePhotoRepository;


/**
 * Device Photo Should Exists Validator
 * @author sergiosanchezsanchez
 *
 */
public class DevicePhotoShouldExistsAndEnabledValidator implements ConstraintValidator<DevicePhotoShouldExistsAndEnabled, String> {
    
	/**
	 * Device Photo  Repository
	 */
    @Autowired
    private DevicePhotoRepository devicePhotoRepository;
    
    /**
     * @param constraintAnnotation
     */
    @Override
    public void initialize(DevicePhotoShouldExistsAndEnabled constraintAnnotation) {}

    /**
     * 
     */
    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
    	return id != null && !id.isEmpty() 
    			&& ObjectId.isValid(id) && devicePhotoRepository.countByIdAndDisabledFalse(new ObjectId(id)) > 0;
    }
}
