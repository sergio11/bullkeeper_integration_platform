package sanchez.sanchez.sergio.masoc.persistence.constraints;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;


/**
 * Weekly Frequency Validate
 * @author sergiosanchezsanchez
 *
 */
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = WeeklyFrequencyValidator.class)
@Documented
public @interface WeeklyFrequencyValidate
{
    String message() default "{constraints.fieldmatch}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    /**
     * @return Start At Field Name
     */
    String startAtFieldName();
    /**
     * @return Weekly Frequency
     */
    String weeklyFrequency();
    /**
     * Defines several <code>@FieldMatch</code> annotations on the same element
     *
     * @see WeeklyFrequencyValidate
     */
    @Target({TYPE, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
            @interface List
    {
        WeeklyFrequencyValidate[] value();
    }
}
