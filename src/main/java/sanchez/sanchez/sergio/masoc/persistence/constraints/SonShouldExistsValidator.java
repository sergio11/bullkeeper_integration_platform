package sanchez.sanchez.sergio.masoc.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.masoc.persistence.repository.SonRepository;
import sanchez.sanchez.sergio.masoc.web.rest.controller.ChildrenController;

public class SonShouldExistsValidator implements ConstraintValidator<SonShouldExists, String> {
	
	private static Logger logger = LoggerFactory.getLogger(SonShouldExistsValidator.class);
    
    @Autowired
    private SonRepository sonRepository;
    
    @Override
    public void initialize(SonShouldExists constraintAnnotation) {}

    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
    	logger.debug("Son Id -> " + id);
    	return id == null || (id != null && id.isEmpty()) || !ObjectId.isValid(id) || sonRepository.countById(new ObjectId(id)) > 0 ? Boolean.TRUE : Boolean.FALSE;
    }
}
