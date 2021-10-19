package com.sbproject.schedule.services.implementations;

import com.sbproject.schedule.controllers.LessonController;
import com.sbproject.schedule.models.*;
import com.sbproject.schedule.repositories.LessonRepository;
import com.sbproject.schedule.repositories.SubjectRepository;
import com.sbproject.schedule.repositories.TeacherRepository;
import com.sbproject.schedule.services.interfaces.LessonService;
import com.sbproject.schedule.utils.Markers;
import com.sbproject.schedule.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.Optional;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    private Utils utils;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private TeacherRepository teacherRepository;
    //private Utils processor;

//    @Autowired
//    public void setProcessor(Utils processor) {
//        this.processor = processor;
//    }

    private static Logger logger = LogManager.getLogger(LessonServiceImpl.class);


    @Override
    public boolean addLesson(Lesson.Time time, Long subjId, Long teachId, SubjectType subjectType, String weeks, Room r, DayOfWeek dayOfWeek) {

        Optional<Subject> s = subjectRepository.findById(subjId);
        Optional<Teacher> t = teacherRepository.findById(teachId);

        if(s.isEmpty() || t.isEmpty()) return false;
        Subject subject = s.get();
        Teacher teacher = t.get();

        lessonRepository.save(new Lesson(time,subject,teacher,subjectType,weeks,r,dayOfWeek));
        return true;
    }

    @Override
    public void deleteLesson(Long id) {
        lessonRepository.deleteById(id);
        logger.info(Markers.DELETE_LESSON_MARKER,"Lesson successfully deleted!");
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
