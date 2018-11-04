package sanchez.sanchez.sergio.masoc.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.masoc.persistence.repository.ITerminalRepository;

/**
 * Terminal Should Exists Validator
 * @author sergiosanchezsanchez
 *
 */
public class TerminalShouldExistsValidator implements ConstraintValidator<TerminalShouldExists, String> {
    
	/**
	 * Terminal Repository
	 */
    @Autowired
    private ITerminalRepository terminalRepository;
    
    @Override
    public void initialize(TerminalShouldExists constraintAnnotation) {}

    /**
     * 
     */
    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
    	return !ObjectId.isValid(id) || terminalRepository.countById(new ObjectId(id)) > 0 ? Boolean.TRUE : Boolean.FALSE;
    }
}
