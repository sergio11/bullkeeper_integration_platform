package es.bisite.usal.bullytect.persistence.constraints;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = FacebookInfoShouldValidValidator.class)
@Documented
public @interface FacebookInfoShouldValid
{
    String message() default "{user.facebook.info.invalid}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    /**
     * @return The first field
     */
    String fbId();
    /**
     * @return The second field
     */
    String fbAccessToken();
    /**
     * Defines several <code>@FieldMatch</code> annotations on the same element
     *
     * @see FacebookInfoShouldValid
     */
    @Target({TYPE, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
            @interface List
    {
        FacebookInfoShouldValid[] value();
    }
}
