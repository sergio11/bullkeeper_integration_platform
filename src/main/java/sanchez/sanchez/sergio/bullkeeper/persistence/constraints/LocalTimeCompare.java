package sanchez.sanchez.sergio.bullkeeper.persistence.constraints;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


/**
 * Local Time Compare
 * @author sergiosanchezsanchez
 *
 */
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = LocalTimeCompareValidator.class)
@Documented
public @interface LocalTimeCompare
{
    String message() default "{constraints.fieldmatch}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    /**
     * @return The first field
     */
    String first();
    /**
     * @return The second field
     */
    String second();
    /**
     * Defines several <code>@FieldMatch</code> annotations on the same element
     *
     * @see LocalTimeCompare
     */
    @Target({TYPE, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
            @interface List
    {
        LocalTimeCompare[] value();
    }
}
