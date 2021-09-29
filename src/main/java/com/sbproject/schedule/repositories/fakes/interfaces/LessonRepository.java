package com.sbproject.schedule.repositories.fakes.interfaces;

import com.sbproject.schedule.models.Lesson;

public interface LessonRepository {

    Iterable<Lesson> findAll();
    Lesson findById(Long id);

    Lesson save(Lesson s);
    void deleteById(Long id);

}
