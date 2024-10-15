package com.example.product.exception;

import com.example.product.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleProductNotFoundException(ProductNotFoundException productNotFoundException) {
        return new ErrorResponse(productNotFoundException.getMessage());
    }

    @ExceptionHandler(ProductBadRequestException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleProductBadRequest(ProductBadRequestException productBadRequestException) {
        return new ErrorResponse(productBadRequestException.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleProductBadRequestViolationConstraint(
            MethodArgumentNotValidException constraintViolationException) {
        Map<String, String> errors = new HashMap<>();
        StringBuilder message = new StringBuilder();
        constraintViolationException.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
            message.append(error.getDefaultMessage());
        });

        return new ErrorResponse(message.toString());
    }

}
