package com.sbproject.schedule.services.interfaces;

import com.sbproject.schedule.exceptions.lesson.NoLessonWithSuchIdFound;
import com.sbproject.schedule.models.*;

import java.time.DayOfWeek;

public interface LessonService {

    Lesson getLessonById(Long id) throws NoLessonWithSuchIdFound;
    Iterable<Lesson> getAll();
    Lesson updateLesson(Long id, Lesson.Time value, Long subjId, Long teachId, SubjectType subjectType, String weeks, Room r, DayOfWeek of);
    Lesson updateLesson(Lesson lesson) throws NoLessonWithSuchIdFound;
    Lesson addLesson(Lesson.Time value, Long subjId, Long teachId, SubjectType subjectType, String weeks, Room r, DayOfWeek of);
    Lesson addLesson(Lesson lesson);
    boolean lessonExistsById(Long id);
    void deleteLesson(Long id) throws NoLessonWithSuchIdFound;

}
