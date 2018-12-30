package sanchez.sanchez.sergio.bullkeeper.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.AppStatsRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * App Stats Should Exists If Present Validator
 * @author sergiosanchezsanchez
 *
 */
public class AppStatsShouldExistsIfPresentValidator 
	implements ConstraintValidator<AppStatsShouldExistsIfPresent, String> {
    
    private static Logger logger = LoggerFactory.getLogger(AppStatsShouldExistsIfPresentValidator.class);
    
    /**
     * App Stats Repository
     */
    @Autowired
    private AppStatsRepository appStatsRepository;
    
    /**
     * 
     */
    @Override
    public void initialize(AppStatsShouldExistsIfPresent constraintAnnotation) {}

    /**
     * Is valid
     */
    @Override
    public boolean isValid(String appStatsId, ConstraintValidatorContext context) {
    	return ( appStatsId == null || appStatsId.isEmpty() ) || 
    			( ObjectId.isValid(appStatsId) && appStatsRepository.countById(new ObjectId(appStatsId)) > 0 
    						? Boolean.TRUE : Boolean.FALSE);
    }
}
