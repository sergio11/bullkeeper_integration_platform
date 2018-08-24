package sanchez.sanchez.sergio.masoc.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.masoc.persistence.repository.SchoolRepository;

public class SchoolMustExistsValidator implements ConstraintValidator<SchoolMustExists, String> {
    
    private static Logger logger = LoggerFactory.getLogger(SchoolMustExistsValidator.class);
    
    @Autowired
    private SchoolRepository schoolRepository;

    @Override
    public void initialize(SchoolMustExists constraintAnnotation) {}

    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
    	return id == null  ||  (!id.isEmpty() && schoolRepository.exists(new ObjectId(id)));
    }
}
