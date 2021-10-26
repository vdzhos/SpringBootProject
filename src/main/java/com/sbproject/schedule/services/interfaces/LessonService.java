package com.sbproject.schedule.services.interfaces;

import com.sbproject.schedule.models.*;

import java.time.DayOfWeek;

public interface LessonService {

    Lesson getLessonById(Long id) throws Exception;
    Iterable<Lesson> getAll();
    boolean updateLesson(Long id, Lesson.Time time, Subject subject, Teacher teacher, SubjectType group,
                         String weeks, Room room, DayOfWeek dayOfWeek);
    Lesson updateLesson(Lesson lesson) throws Exception;
    boolean addLesson(Lesson.Time value, Long subjId, Long teachId, SubjectType subjectType, String weeks, Room r, DayOfWeek of);
    Lesson addLesson(Lesson lesson);
    boolean lessonExistsById(Long id);
    void deleteLesson(Long id) throws Exception;

}
