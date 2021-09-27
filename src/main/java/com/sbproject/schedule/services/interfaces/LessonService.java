package com.sbproject.schedule.services.interfaces;

import com.sbproject.schedule.models.*;

import java.time.DayOfWeek;

public interface LessonService {

    boolean addLesson(Lesson.Time time, Subject subject, Teacher teacher, Lesson.SubjectType group,
                      String weeks, Lesson.Room room, DayOfWeek dayOfWeek);
    void deleteLesson(Long id);
    boolean updateLesson(Long id, Lesson.Time time, Subject subject, Teacher teacher, Lesson.SubjectType group,
                         String weeks, Lesson.Room room, DayOfWeek dayOfWeek);
    Iterable<Lesson> getAll();

}
