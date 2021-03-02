package com.spring.boot.SpringSample.validator.implementation;

import com.spring.boot.SpringSample.service.ValidationService;
import com.spring.boot.SpringSample.validator.IsValidId;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class IsValidIdValidator implements ConstraintValidator<IsValidId, Object> {
    private String tableName;
    private boolean ignoreZero;

    private final ValidationService validationService;

    @Autowired
    public IsValidIdValidator(ValidationService validationService) {
        this.validationService = validationService;
    }

    @Override
    public void initialize(IsValidId annotation) {
        tableName = annotation.tableName();
        ignoreZero = annotation.ignoreZero();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        if (value instanceof Long) {
            if (!ignoreZero && (Long) value == 0) {
                return false;
            } else if (ignoreZero && (Long) value == 0) {
                return true;
            }
            return !validationService.isValidId(tableName, (Long) value);
        } else if (value instanceof List) {

            @SuppressWarnings("unchecked")
            List<Long> ids = (List<Long>) value;

            if (ids.isEmpty()) {
                return false;
            }

            if (ids.stream().anyMatch((i) -> (validationService.isValidId(tableName, i)))) {
                return false;
            }
        }
        return true;
    }
}
