package sanchez.sanchez.sergio.bullkeeper.persistence.constraints;


import java.text.SimpleDateFormat;
import java.util.Date;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.joda.time.LocalDate;
import org.joda.time.Years;


public class InAgeRangeValidator implements ConstraintValidator<InAgeRange, java.util.Date> {
    
    private static final SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd");
    
    private InAgeRange constraintAnnotation;
    
    @Override
    public void initialize(InAgeRange constraintAnnotation) {
        this.constraintAnnotation = constraintAnnotation;
    }
    
    private Integer getAgeFor(Date value) {

        Integer years = 0;
        if ((value != null)) {
            Years age = Years.yearsBetween(LocalDate.fromDateFields(value), LocalDate.now());
            years = age.getYears();
        }

        return years;
    }
    
    @Override
    public boolean isValid(java.util.Date value, ConstraintValidatorContext context) {
        boolean isValid = Boolean.FALSE;
        if(value != null) {
            final Integer min = Integer.parseInt(constraintAnnotation.min());
            final Integer max = Integer.parseInt(constraintAnnotation.max());
            final Integer age = getAgeFor(value);
            isValid = (age >= min && age <= max);
        }
      
        return isValid;
    }
}
