package at.arz.latte.framework.validator;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * annotation for email validation
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@Constraint(validatedBy = EMailValidator.class)
@Target({ METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Documented
public @interface EMail {
	String message() default "Ungültige E-Mail-Adresse";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}