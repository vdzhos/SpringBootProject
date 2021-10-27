package com.sbproject.schedule.exceptions.lesson;


public class NoLessonWithSuchIdToDelete extends Exception {

    public NoLessonWithSuchIdToDelete(Long id) {
        super("Lesson with id '"+ id +"' not found!");
    }

}
