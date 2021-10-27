package com.sbproject.schedule.exceptions.specialty;


public class InvalidSpecialtyNameException extends RuntimeException {

    public InvalidSpecialtyNameException(String explanation) {
        super(explanation);
    }
}
