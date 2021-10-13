package com.sbproject.schedule.services.implementations;

import com.sbproject.schedule.models.*;
import com.sbproject.schedule.repositories.LessonRepository;
import com.sbproject.schedule.services.interfaces.LessonService;
import com.sbproject.schedule.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    private Utils utils;

    private LessonRepository lessonRepository;
    //private Utils processor;

//    @Autowired
//    public void setProcessor(Utils processor) {
//        this.processor = processor;
//    }

    @Autowired
    public void setLessonRepository(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Override
    public boolean addLesson(Lesson.Time time, Subject subject, Teacher teacher, SubjectType group, String weeks, Room room, DayOfWeek dayOfWeek) {
        lessonRepository.save(new Lesson(utils.getUniqueId(),time,subject,teacher,group,weeks,room,dayOfWeek));
        return true;
    }

    @Override
    public void deleteLesson(Long id) {
        lessonRepository.deleteById(id);
    }

    @Override
    public boolean updateLesson(Long id, Lesson.Time time, Subject subject, Teacher teacher, SubjectType group, String weeks, Room room, DayOfWeek dayOfWeek) {
        lessonRepository.save(new Lesson(id,time,subject,teacher,group,weeks,room,dayOfWeek));
        return true;
    }

    @Override
    public Iterable<Lesson> getAll() {
        return lessonRepository.findAll();
    }
}
