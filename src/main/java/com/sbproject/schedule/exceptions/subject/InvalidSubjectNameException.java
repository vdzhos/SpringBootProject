package com.sbproject.schedule.exceptions.subject;

public class InvalidSubjectNameException extends RuntimeException {

    public InvalidSubjectNameException(String explanation) {
        super(explanation);
    }
}
