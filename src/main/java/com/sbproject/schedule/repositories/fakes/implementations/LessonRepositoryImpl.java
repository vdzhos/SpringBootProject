package com.sbproject.schedule.repositories.fakes.implementations;

import com.sbproject.schedule.models.Lesson;
import com.sbproject.schedule.repositories.fakes.interfaces.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class LessonRepositoryImpl implements LessonRepository {

    private Map<Long, Lesson> lessonMap;

    @Autowired
    public LessonRepositoryImpl(Map<Long, Lesson> lessonMap) {
        this.lessonMap = lessonMap;
    }

    @Override
    public Iterable<Lesson> findAll() {
        return lessonMap.values();
    }

    @Override
    public Lesson findById(Long id) {
        return lessonMap.get(id);
    }

    @Override
    public Lesson save(Lesson l) {
        lessonMap.put(l.getId(),l);
        System.out.println("Saved lesson: \n"+l);
        return l;
    }

    @Override
    public void deleteById(Long id) {
        lessonMap.remove(id);
        System.out.println("Lesson with id = "+id +" has been deleted!");
    }
}
