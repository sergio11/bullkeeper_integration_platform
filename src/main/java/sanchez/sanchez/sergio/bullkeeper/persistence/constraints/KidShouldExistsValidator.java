package sanchez.sanchez.sergio.bullkeeper.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.bullkeeper.persistence.repository.KidRepository;
import sanchez.sanchez.sergio.bullkeeper.web.rest.controller.ChildrenController;

public class KidShouldExistsValidator implements ConstraintValidator<KidShouldExists, String> {
	
	private static Logger logger = LoggerFactory.getLogger(KidShouldExistsValidator.class);
    
    @Autowired
    private KidRepository kidRepository;
    
    @Override
    public void initialize(KidShouldExists constraintAnnotation) {}

    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
    	logger.debug("Son Id -> " + id);
    	return id == null || (id != null && id.isEmpty()) || !ObjectId.isValid(id) || 
    			kidRepository.countById(new ObjectId(id)) > 0 ? Boolean.TRUE : Boolean.FALSE;
    }
}
