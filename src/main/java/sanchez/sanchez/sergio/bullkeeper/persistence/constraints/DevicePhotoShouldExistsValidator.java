package sanchez.sanchez.sergio.bullkeeper.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.bullkeeper.persistence.repository.AppInstalledRepository;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Device Photos Should Exists
 * @author sergiosanchezsanchez
 *
 */
public class DevicePhotoShouldExistsValidator implements ConstraintValidator<DevicePhotoShouldExists, String> {
    
    private static Logger logger = LoggerFactory.getLogger(DevicePhotoShouldExistsValidator.class);

    
    /**
     * 
     */
    @Override
    public void initialize(DevicePhotoShouldExists constraintAnnotation) {}

    /**
     * Is valid
     */
    @Override
    public boolean isValid(String appId, ConstraintValidatorContext context) {
    	return appId != null && !appId.isEmpty() && ObjectId.isValid(appId);
    }
}
