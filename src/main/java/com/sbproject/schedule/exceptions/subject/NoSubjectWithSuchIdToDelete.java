package com.sbproject.schedule.exceptions.subject;

public class NoSubjectWithSuchIdToDelete extends RuntimeException {
    public NoSubjectWithSuchIdToDelete(Long id) {
        super("Subject with id '"+ id +"' has not been found!");
    }
}
