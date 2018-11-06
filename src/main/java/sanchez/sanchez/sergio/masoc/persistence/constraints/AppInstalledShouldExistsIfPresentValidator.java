package sanchez.sanchez.sergio.masoc.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import sanchez.sanchez.sergio.masoc.persistence.repository.IAppInstalledRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * App Installed Should Exists If Present Validator
 * @author sergiosanchezsanchez
 *
 */
public class AppInstalledShouldExistsIfPresentValidator implements ConstraintValidator<AppInstalledShouldExistsIfPresent, String> {
    
    private static Logger logger = LoggerFactory.getLogger(AppInstalledShouldExistsIfPresentValidator.class);
    
    /**
     * App installed Repository
     */
    @Autowired
    private IAppInstalledRepository appInstalledRepository;
    
    /**
     * 
     */
    @Override
    public void initialize(AppInstalledShouldExistsIfPresent constraintAnnotation) {}

    /**
     * Is valid
     */
    @Override
    public boolean isValid(String appId, ConstraintValidatorContext context) {
    	return ( appId == null || appId.isEmpty() ) || 
    			( ObjectId.isValid(appId) && appInstalledRepository.countById(new ObjectId(appId)) > 0 
    						? Boolean.TRUE : Boolean.FALSE);
    }
}
