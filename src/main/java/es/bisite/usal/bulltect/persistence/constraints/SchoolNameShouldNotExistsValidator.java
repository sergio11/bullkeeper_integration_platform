package es.bisite.usal.bulltect.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import es.bisite.usal.bulltect.persistence.repository.SchoolRepository;

public class SchoolNameShouldNotExistsValidator implements ConstraintValidator<SchoolNameShouldNotExists, String> {
    
    private static Logger logger = LoggerFactory.getLogger(SchoolNameShouldNotExistsValidator.class);
    
    @Autowired
    private SchoolRepository schoolRepository;

    @Override
    public void initialize(SchoolNameShouldNotExists constraintAnnotation) {}

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
    	logger.debug("School Name -> " + name);
    	return name != null &&  !name.isEmpty() && schoolRepository.countByName(name) == 0;
    }
}
