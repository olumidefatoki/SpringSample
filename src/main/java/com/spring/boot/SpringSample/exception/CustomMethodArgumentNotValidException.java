package com.spring.boot.SpringSample.exception;

import com.spring.boot.SpringSample.domain.response.ErrorModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;


@Data
@AllArgsConstructor
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Validation error")
@EqualsAndHashCode(callSuper = false)
public class CustomMethodArgumentNotValidException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -6293664269727446738L;
    public List<ErrorModel> errorMessages;

    public CustomMethodArgumentNotValidException(String message) {
        super(message);
        // this.errorMessages = errorMessages;
    }
}
