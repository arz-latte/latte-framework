package at.arz.latte.framework.modules.models.validator;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = CheckUrlValidator.class)
@Documented
public @interface CheckUrl {
	String message() default "Ungültige URL";

	Class<?>[]groups() default {};

	Class<? extends Payload>[]payload() default {};
}
