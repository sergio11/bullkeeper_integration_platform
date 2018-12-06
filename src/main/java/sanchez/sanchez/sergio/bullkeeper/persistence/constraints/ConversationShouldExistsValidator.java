package sanchez.sanchez.sergio.bullkeeper.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.bullkeeper.persistence.repository.ConversationRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.SupervisedChildrenRepository;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Conversation Should Exists Validator
 * @author sergiosanchezsanchez
 *
 */
public class ConversationShouldExistsValidator 
		implements ConstraintValidator<ConversationShouldExists, String> {
    
    private static Logger logger = LoggerFactory.getLogger(ConversationShouldExistsValidator.class);
    
    /**
     * Conversation Repository
     */
    @Autowired
    protected ConversationRepository conversationRepository;
    
    /**
     * 
     */
    @Override
    public void initialize(ConversationShouldExists constraintAnnotation) {}

    /**
     * Is valid
     */
    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
    	return id != null && !id.isEmpty() && ObjectId.isValid(id) && 
    			conversationRepository.countById(new ObjectId(id)) > 0;
    }
}
