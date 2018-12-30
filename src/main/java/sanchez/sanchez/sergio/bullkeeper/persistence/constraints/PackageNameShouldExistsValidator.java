package sanchez.sanchez.sergio.bullkeeper.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.bullkeeper.persistence.repository.AppInstalledRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * App Installed Should Exists
 * @author sergiosanchezsanchez
 *
 */
public class PackageNameShouldExistsValidator implements ConstraintValidator<PackageNameShouldExists, String> {
    
    private static Logger logger = LoggerFactory.getLogger(PackageNameShouldExistsValidator.class);
    
    /**
     * App installed Repository
     */
    @Autowired
    private AppInstalledRepository appInstalledRepository;
    
    /**
     * 
     */
    @Override
    public void initialize(PackageNameShouldExists constraintAnnotation) {}

    /**
     * Is valid
     */
    @Override
    public boolean isValid(String packageName, ConstraintValidatorContext context) {
    	return packageName != null && !packageName.isEmpty() && 
    			appInstalledRepository.countByPackageName(packageName) > 0;
    }
}
