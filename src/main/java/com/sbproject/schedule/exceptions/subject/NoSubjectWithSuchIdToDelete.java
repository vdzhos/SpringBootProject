package com.sbproject.schedule.exceptions.subject;

public class NoSubjectWithSuchIdToDelete extends Exception {
    public NoSubjectWithSuchIdToDelete(Long id) {
        super("Subject with id '"+ id +"' has not been found!");
    }
}
