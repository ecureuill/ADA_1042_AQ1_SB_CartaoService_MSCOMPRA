package com.ada.grupo5.mscompra.advice;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
    public Map<String, String> handleInvalidConstraint(ConstraintViolationException constraintViolationException) {
        Map<String, String>errorMap = new HashMap<>();
        constraintViolationException.getConstraintViolations()
                .forEach(error -> {
                    errorMap.put("error",error.getMessage());
                });
        return errorMap;
    }
}
