package sanchez.sanchez.sergio.masoc.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import sanchez.sanchez.sergio.masoc.persistence.repository.SchoolRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * School Email Should Exists if present validator
 * @author sergiosanchezsanchez
 *
 */
public class SchoolEmailShouldExistsIfPresentValidator implements ConstraintValidator<SchoolEmailShouldExistsIfPresent, String> {
    
    private static Logger logger = LoggerFactory.getLogger(SchoolEmailShouldExistsIfPresentValidator.class);
    
    /**
     * School Repository
     */
    @Autowired
    private SchoolRepository schoolRepository;
    
    /**
     * 
     */
    @Override
    public void initialize(SchoolEmailShouldExistsIfPresent constraintAnnotation) {}

    /**
     * Is valid
     */
    @Override
    public boolean isValid(String schoolEmail, ConstraintValidatorContext context) {
    	return ( schoolEmail == null || schoolEmail.isEmpty() ) || 
    			( schoolRepository.countByEmail(schoolEmail) == 0);
    }
}
