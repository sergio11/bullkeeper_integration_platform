package sanchez.sanchez.sergio.bullkeeper.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.SupervisedChildrenRepository;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Supervised Children Should Exists validator
 * @author sergiosanchezsanchez
 *
 */
public class SupervisedChildrenShouldExistsValidator 
		implements ConstraintValidator<SupervisedChildrenShouldExists, String> {
    
    private static Logger logger = LoggerFactory.getLogger(SupervisedChildrenShouldExistsValidator.class);
    
    /**
     * Supervised Children Repository
     */
    @Autowired
    protected SupervisedChildrenRepository supervisedChildrenRepository;
    
    /**
     * 
     */
    @Override
    public void initialize(SupervisedChildrenShouldExists constraintAnnotation) {}

    /**
     * Is valid
     */
    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
    	return id != null && !id.isEmpty() && ObjectId.isValid(id) && 
    					supervisedChildrenRepository.countById(new ObjectId(id)) > 0;
    }
}
