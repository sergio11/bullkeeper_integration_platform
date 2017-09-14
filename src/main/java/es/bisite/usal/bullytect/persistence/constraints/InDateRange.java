package es.bisite.usal.bullytect.persistence.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = InDateRangeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface InDateRange {
 // used to get later in the resource bundle the i18n text
 String message() default "{validation.date.InDateRange.message}";
 Class<?>[] groups() default {};
 Class<? extends Payload>[] payload() default {};
 // min value, we for now just a string
 String min() default "1900-01-01";
 // max date value we support
 String max() default "2999-12-31";
}
