package com.spring.boot.SpringSample.validator.implementation;


import com.spring.boot.SpringSample.service.ValidationService;
import com.spring.boot.SpringSample.validator.IsExist;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class IsExistValidator implements ConstraintValidator<IsExist, Object> {

    private String tableName;
    private String columnName;
    private boolean required;

    private final ValidationService validationService;

    @Autowired
    public IsExistValidator(ValidationService validationService) {
        this.validationService = validationService;
    }

    @Override
    public void initialize(IsExist annotation) {
        tableName = annotation.tableName();
        columnName = annotation.columnName();
        required = annotation.required();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext cvc) {
        return (!required || !StringUtils.isBlank(value.toString()))
                && (!required && StringUtils.isBlank(value.toString())
                || !validationService.isExist(tableName, columnName, value));
    }
}
