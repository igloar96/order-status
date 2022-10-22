package dev.byli.orderstatus.v1.config;

import dev.byli.orderstatus.v1.exception.NotFoundException;
import feign.FeignException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionsHandler {
    public static final String ERROR_RESPONSE_CAUSE = "cause";

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FeignException.class)
    public Map<String, String> handleFeignExceptions(
        FeignException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(ERROR_RESPONSE_CAUSE,ex.getLocalizedMessage());
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
        MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
            errors.put(ERROR_RESPONSE_CAUSE,ex.getLocalizedMessage());
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public Map<String, String> notFound(NotFoundException e){
        Map<String, String> errors = new HashMap<>();
        errors.put(ERROR_RESPONSE_CAUSE,e.getLocalizedMessage());
        return errors;
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public Map<String, String> dbConflict(EmptyResultDataAccessException e){
        Map<String, String> errors = new HashMap<>();
        errors.put(ERROR_RESPONSE_CAUSE,e.getLocalizedMessage());
        return errors;
    }
}
