package sanchez.sanchez.sergio.masoc.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import sanchez.sanchez.sergio.masoc.persistence.repository.AppInstalledRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * App Installed Should Exists If Present Validator
 * @author sergiosanchezsanchez
 *
 */
public class SchoolNameShouldExistsIfPresentValidator implements ConstraintValidator<AppInstalledShouldExistsIfPresent, String> {
    
    private static Logger logger = LoggerFactory.getLogger(SchoolNameShouldExistsIfPresentValidator.class);
    
    /**
     * App installed Repository
     */
    @Autowired
    private AppInstalledRepository appInstalledRepository;
    
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
