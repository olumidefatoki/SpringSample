package com.spring.boot.SpringSample.validator.implementation;

import com.spring.boot.SpringSample.service.ValidationService;
import com.spring.boot.SpringSample.validator.IsUnique;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class IsUniqueValidator implements ConstraintValidator<IsUnique, Object> {

    private String tableName;
    private String columnName;
    private boolean required;
    private boolean shouldBeUnique;

    @Autowired
    ValidationService validationService;

    @Override
    public void initialize(IsUnique annotation) {
        tableName = annotation.tableName();
        columnName = annotation.columnName();
        required = annotation.required();
        shouldBeUnique = annotation.shouldBeUnique();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext cvc) {
        if (!required && StringUtils.isBlank(value.toString())) {
            return false;
        }
        
        return shouldBeUnique  && validationService.isUnique(tableName, columnName, value);
    }
}
