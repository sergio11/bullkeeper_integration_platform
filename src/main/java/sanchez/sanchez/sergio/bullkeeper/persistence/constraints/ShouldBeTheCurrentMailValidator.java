package sanchez.sanchez.sergio.bullkeeper.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IAuthorizationService;

/**
 * 
 * @author sergiosanchezsanchez
 *
 */
public class ShouldBeTheCurrentMailValidator implements ConstraintValidator<ShouldBeTheCurrentMail, String> {
    
    private static Logger logger = LoggerFactory.getLogger(ShouldBeTheCurrentMailValidator.class);

    @Autowired
    private IAuthorizationService authorizationService;

    @Override
    public void initialize(ShouldBeTheCurrentMail constraintAnnotation) {}

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
    	return authorizationService.getUserDetails().getEmail().equals(email);
    }
}
