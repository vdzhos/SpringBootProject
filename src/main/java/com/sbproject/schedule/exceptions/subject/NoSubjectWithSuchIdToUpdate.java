package com.sbproject.schedule.exceptions.subject;

public class NoSubjectWithSuchIdToUpdate extends RuntimeException {
    public NoSubjectWithSuchIdToUpdate(Long id) {
        super("Subject with id '"+ id +"' has not been found!");
    }
}
