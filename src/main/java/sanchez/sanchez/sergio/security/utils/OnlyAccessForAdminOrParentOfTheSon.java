package sanchez.sanchez.sergio.security.utils;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

/**
 *
 * @author sergio
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasParentRole() && @authorizationService.isYourSon(#son) )")
public @interface OnlyAccessForAdminOrParentOfTheSon {
	String son() default "";
}
