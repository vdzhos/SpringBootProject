package com.sbproject.schedule.services.implementations;

import com.sbproject.schedule.database.Database;
import com.sbproject.schedule.models.Lesson;
import com.sbproject.schedule.models.Subject;
import com.sbproject.schedule.models.Teacher;
import com.sbproject.schedule.repositories.fakes.interfaces.LessonRepository;
import com.sbproject.schedule.services.interfaces.LessonService;
import com.sbproject.schedule.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;

@Service
public class LessonServiceImpl implements LessonService {

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
    public boolean addLesson(Lesson.Time time, Subject subject, Teacher teacher, Lesson.SubjectType group, String weeks, Lesson.Room room, DayOfWeek dayOfWeek) {
        lessonRepository.save(new Lesson(Database.getUniqueId(),time,subject,teacher,group,weeks,room,dayOfWeek));
        return true;
    }

    @Override
    public void deleteLesson(Long id) {
        lessonRepository.deleteById(id);
    }

    @Override
    public boolean updateLesson(Long id, Lesson.Time time, Subject subject, Teacher teacher, Lesson.SubjectType group, String weeks, Lesson.Room room, DayOfWeek dayOfWeek) {
        lessonRepository.save(new Lesson(id,time,subject,teacher,group,weeks,room,dayOfWeek));
        return true;
    }

    @Override
    public Iterable<Lesson> getAll() {
        return lessonRepository.findAll();
    }
}
