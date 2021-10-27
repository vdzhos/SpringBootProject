package com.sbproject.schedule.exceptions.lesson;

public class NoLessonWithSuchIdToUpdate extends Exception {

    public NoLessonWithSuchIdToUpdate(Long id) {
        super("Lesson with id '"+ id +"' not found!");
    }

}