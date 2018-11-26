package sanchez.sanchez.sergio.bullkeeper.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.SupervisedChildrenRepository;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Supervised Children Should Exists if present validator
 * @author sergiosanchezsanchez
 *
 */
public class SupervisedChildrenShouldExistsIfPresentValidator 
		implements ConstraintValidator<SupervisedChildrenShouldExistsIfPresent, String> {
    
    private static Logger logger = LoggerFactory.getLogger(SupervisedChildrenShouldExistsIfPresentValidator.class);
    
    /**
     * Supervised Children Repository
     */
    @Autowired
    protected SupervisedChildrenRepository supervisedChildrenRepository;
    
    /**
     * 
     */
    @Override
    public void initialize(SupervisedChildrenShouldExistsIfPresent constraintAnnotation) {}

    /**
     * Is valid
     */
    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
    	return ( id == null || id.isEmpty() ) || 
    			( ObjectId.isValid(id) && 
    					supervisedChildrenRepository.countById(new ObjectId(id)) > 0);
    }
}
