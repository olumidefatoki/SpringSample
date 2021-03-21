package com.spring.boot.SpringSample.exception;


import com.google.gson.Gson;
import com.spring.boot.SpringSample.domain.response.ApiResponse;
import com.spring.boot.SpringSample.domain.response.ErrorModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.DataBinder;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ExceptionHandlers {

    @InitBinder
    private void activateDirectFieldAccess(DataBinder dataBinder) {
        dataBinder.initDirectFieldAccess();
    }

   

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResponse<?> handleUserNotFoundException(final BadRequestException ex) {
        log.error("Not found exception thrown");

        return ApiResponse.<String>builder().data("").status(HttpStatus.BAD_REQUEST.value()).message(ex.getMessage())
                .error(ex.getMessage()).build();
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResponse<?> methodArgumentNotValidException(final MethodArgumentNotValidException ex) {

        List<ErrorModel> errorMessages = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> new ErrorModel(err.getField(), err.getRejectedValue(), err.getDefaultMessage())).distinct()
                .collect(Collectors.toList());

        Map<String, String> errorResponse = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            if (errorResponse.put(fieldError.getField(), fieldError.getDefaultMessage()) != null) {
                throw new IllegalStateException("Duplicate key");
            }
        }

        ApiResponse<?> response = ApiResponse.<String>builder().data("").error(errorResponse)
                .status(HttpStatus.BAD_REQUEST.value()).message("Input Validation Error").build();

        /* Added to fix class level validator exception **/
        if (errorMessages.isEmpty() && ex.getBindingResult().getAllErrors().size() > 0) {
            for (ObjectError objectError : ex.getBindingResult().getAllErrors()) {
                errorMessages.add(new ErrorModel("not_applicable", "Not Applicable", objectError.getDefaultMessage()));
            }
            response = ApiResponse.<String>builder().data("").error(errorMessages)
                    .status(4000).message("Input Validation Error").build();
        }


        log.error("MethodArgumentNotValidException- {}", new Gson().toJson(response));

        return response;
    }

    @ExceptionHandler(CustomMethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResponse<?> customMethodArgumentNotValidException(final CustomMethodArgumentNotValidException ex) {

        ApiResponse<?> response = ApiResponse.<String>builder().data("").error(ex.errorMessages)
                .status(HttpStatus.BAD_REQUEST.value()).message("Input Validation Error").build();

        log.error("CustomMethodArgumentNotValidException- {}", new Gson().toJson(response));

        return response;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResponse<?> constraintViolationException(final ConstraintViolationException ex) {

        List<ErrorModel> errorMessages = ex.getConstraintViolations().stream()
                .map(err -> new ErrorModel(err.getPropertyPath().toString(), err.getInvalidValue(), err.getMessage()))
                .distinct().collect(Collectors.toList());

        return ApiResponse.builder().data("").status(HttpStatus.BAD_REQUEST.value())
                .message("Constraint Violations Error").error(errorMessages).build();
    }

    @ExceptionHandler(NoResultException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ApiResponse<?> noResultException(final NoResultException ex) {

        return ApiResponse.builder().data("").status(HttpStatus.NOT_FOUND.value())
                .message("The requested record not found!").error(ex.getMessage()).build();
    }


}
