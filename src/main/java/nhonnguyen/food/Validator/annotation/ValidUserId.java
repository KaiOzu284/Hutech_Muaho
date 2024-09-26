package nhonnguyen.food.Validator.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import nhonnguyen.food.Validator.ValidUserIdValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = ValidUserIdValidator.class)
@Documented
public @interface ValidUserId {
    String message() default "Invalid User id";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}