package com.spring.boot.SpringSample.validator;


import com.spring.boot.SpringSample.validator.implementation.IsExistValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = IsExistValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface IsExist {

    String tableName();

    String columnName();
    
    boolean shouldBeUnique() default false;

    boolean required() default false;

    public String message() default "Value Not Found";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

}
