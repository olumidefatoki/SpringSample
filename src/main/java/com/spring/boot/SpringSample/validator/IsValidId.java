package com.spring.boot.SpringSample.validator;

import com.spring.boot.SpringSample.validator.implementation.IsValidIdValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = IsValidIdValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface IsValidId {
    String tableName();

    boolean ignoreZero() default false;

    public String message() default "Invalid Id";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}
