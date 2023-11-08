package com.mindhub.homebanking.validation.Annotation;

import com.mindhub.homebanking.validation.MinLessThanMaxValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MinLessThanMaxValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface MinLessThanMax {
    String message() default "Min amount must be less than max amount";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
