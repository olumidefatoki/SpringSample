package com.spring.boot.SpringSample.validator;


import com.spring.boot.SpringSample.validator.implementation.IsUniqueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = IsUniqueValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface IsUnique {

    String tableName();

    String columnName();
    
    boolean shouldBeUnique() default true;

    boolean required() default true;

    public String message() default "In use already";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

}
