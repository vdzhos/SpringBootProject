package com.sbproject.schedule.utils;

import com.sbproject.schedule.exceptions.specialty.InvalidSpecialtyNameException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class UtilsImpl  implements Utils {

    @Override
    public String processName(String name){
        return name.replaceAll("\\s+"," ").trim();
    }

    @Override
    public boolean isInvalidName(String name) {
        return name.isEmpty();
    }

    @Override
    public void checkName(String name) throws InvalidSpecialtyNameException {
        if(isInvalidName(name)){
            throw new InvalidSpecialtyNameException(Values.INVALID_SPECIALTY_NAME);
        }
    }

    @Override
    public Long getUniqueId() {
        return new Random().nextLong()+System.currentTimeMillis();
    }

}
