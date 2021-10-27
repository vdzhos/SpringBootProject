package com.sbproject.schedule.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String,String>> handleInvalidObjectsExceptions(MethodArgumentNotValidException e){
        Map<String,String> map = new HashMap<>();
        map.put("success","false");
        for(FieldError error: e.getFieldErrors()){
            map.put(error.getField(),error.getDefaultMessage());
        }
        return new ResponseEntity<Map<String,String>>(map,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String,String>> handleInvalidParamsExceptions(ConstraintViolationException e){
        Map<String,String> map = new HashMap<>();
        map.put("success","false");
        for(ConstraintViolation<?> violation: e.getConstraintViolations()){
            map.put(violation.getPropertyPath().toString(),violation.getMessage());
        }
        return new ResponseEntity<Map<String,String>>(map,HttpStatus.BAD_REQUEST);
    }
}


