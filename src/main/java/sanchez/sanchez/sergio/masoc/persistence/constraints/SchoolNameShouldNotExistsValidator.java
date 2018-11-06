package sanchez.sanchez.sergio.masoc.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.masoc.persistence.repository.SchoolRepository;

/**
 * School Name Should Not Exists Validator
 * @author sergiosanchezsanchez
 *
 */
public class SchoolNameShouldNotExistsValidator implements ConstraintValidator<SchoolNameShouldNotExists, String> {
    
    private static Logger logger = LoggerFactory.getLogger(SchoolNameShouldNotExistsValidator.class);
    
    @Autowired
    private SchoolRepository schoolRepository;

    @Override
    public void initialize(SchoolNameShouldNotExists constraintAnnotation) {}

    /**
     * Is Valid
     */
    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
    	logger.debug("School Name -> " + name);
    	return name != null &&  !name.isEmpty() 
    			&& schoolRepository.countByNameIgnoreCase(name) == 0;
    }
}
