package sanchez.sanchez.sergio.bullkeeper.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.bullkeeper.domain.service.IAuthorizationService;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.GuardianRepository;

/**
 * 
 * @author sergiosanchezsanchez
 *
 */
public class NewParentEmailShouldNotExistValidator implements ConstraintValidator<NewParentEmailShouldNotExist, String> {
    
    private static Logger logger = LoggerFactory.getLogger(NewParentEmailShouldNotExistValidator.class);
    
    @Autowired
    private GuardianRepository parentRepository;
    
    @Autowired
    private IAuthorizationService authorizationService;

    @Override
    public void initialize(NewParentEmailShouldNotExist constraintAnnotation) {}

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
    	return !authorizationService.getUserDetails().getEmail().equals(email) && 
	    			parentRepository.countByEmail(email) == 0 ? Boolean.TRUE : Boolean.FALSE;
    }
}
