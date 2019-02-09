package sanchez.sanchez.sergio.bullkeeper.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.AppModelCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * App Model Category Should Exists
 * @author sergiosanchezsanchez
 *
 */
public class AppModelCategoryShouldExistsValidator implements ConstraintValidator<AppModelCategoryShouldExists, String> {
    
    private static Logger logger = LoggerFactory.getLogger(AppModelCategoryShouldExistsValidator.class);
    
    /**
     * App Model Category Repository
     */
    @Autowired
    private AppModelCategoryRepository appModelCategoryRepository;
    
    /**
     * 
     */
    @Override
    public void initialize(AppModelCategoryShouldExists constraintAnnotation) {}

    /**
     * Is valid
     */
    @Override
    public boolean isValid(String catKey, ConstraintValidatorContext context) {
    	return catKey != null && !catKey.isEmpty() && 
    			appModelCategoryRepository.countByCatKey(catKey) > 0;
    }
}
