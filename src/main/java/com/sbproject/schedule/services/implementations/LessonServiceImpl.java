package com.sbproject.schedule.services.implementations;

import com.sbproject.schedule.exceptions.lesson.InvalidLessonArgumentsException;
import com.sbproject.schedule.exceptions.lesson.NoLessonWithSuchIdFound;
import com.sbproject.schedule.exceptions.subject.SubjectNotFoundException;
import com.sbproject.schedule.exceptions.teacher.TeacherNotFoundException;
import com.sbproject.schedule.models.*;
import com.sbproject.schedule.repositories.LessonRepository;
import com.sbproject.schedule.repositories.SubjectRepository;
import com.sbproject.schedule.repositories.TeacherRepository;
import com.sbproject.schedule.services.interfaces.LessonService;
import com.sbproject.schedule.utils.Markers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.Optional;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    private static final Logger logger = LogManager.getLogger(LessonServiceImpl.class);

    @Override
    public Lesson addLesson(Lesson.Time time, Long subjId, Long teachId, SubjectType subjectType, String weeks, String room, DayOfWeek dayOfWeek) {
        Object[] res = verifyAndProcessData(subjId,teachId,weeks,room);
        logger.info(Markers.ALTERING_LESSON_TABLE_MARKER,"Lesson successfully added!");
        return lessonRepository.save(new Lesson(time,(Subject) res[1],(Teacher) res[2],subjectType,weeks,(Room) res[0],dayOfWeek));
    }

    @Override
    public Lesson addLesson(Lesson lesson) {
        lesson.setId(-1L);
        return lessonRepository.save(lesson);
    }

    @Override
    public boolean lessonExistsById(Long id) {
        return lessonRepository.existsById(id);
    }

    @Override
    public void deleteLesson(Long id) throws NoLessonWithSuchIdFound {
        if(!lessonExistsById(id)){
            logger.info(Markers.DELETE_LESSON_MARKER,"Lesson not deleted!");
            throw new NoLessonWithSuchIdFound(id,"deleted");
        }
        lessonRepository.deleteById(id);
        logger.info(Markers.DELETE_LESSON_MARKER,"Lesson successfully deleted!");
    }

    @Override
    public Lesson updateLesson(Long id, Lesson.Time time, Long subjId, Long teachId, SubjectType subjectType, String weeks, String room, DayOfWeek dayOfWeek) {
        Object[] res = verifyAndProcessData(subjId,teachId,weeks,room);
        logger.info(Markers.ALTERING_LESSON_TABLE_MARKER,"Lesson successfully added!");
        return lessonRepository.save(new Lesson(id,time,(Subject) res[1],(Teacher) res[2],subjectType,weeks,(Room) res[0],dayOfWeek));
    }

    @Override
    public Lesson updateLesson(Lesson lesson) throws NoLessonWithSuchIdFound {
        if(!lessonExistsById(lesson.getId())) throw new NoLessonWithSuchIdFound(lesson.getId(),"updated");
        return lessonRepository.save(lesson);
    }

    @Override
    public Lesson getLessonById(Long id) throws NoLessonWithSuchIdFound{
        return lessonRepository.findById(id).orElseThrow(() -> new NoLessonWithSuchIdFound(id,"get"));
    }

    @Override
    public Iterable<Lesson> getAll() {
        return lessonRepository.findAll();
    }

    private Object[] verifyAndProcessData(Long subjId, Long teachId, String weeks, String room){
        Room r;
        if(room.equals("remotely")) r = new Room();
        else r = new Room(room);

        if(weeks.isEmpty() || !weeks.matches("^([1-9][0-9]*(-[1-9][0-9]*)?)(,([1-9][0-9]*(-[1-9][0-9]*)?))*$")){
            logger.error(Markers.ALTERING_LESSON_TABLE_MARKER,"Lesson not added!");
            throw new InvalidLessonArgumentsException("weeks",weeks);
        }

        Optional<Subject> s = subjectRepository.findById(subjId);
        Optional<Teacher> t = teacherRepository.findById(teachId);

        if(s.isEmpty()){
            logger.error(Markers.ALTERING_LESSON_TABLE_MARKER,"Lesson not added!");
            throw new SubjectNotFoundException("Subject with id \""+subjId+"\" not found!");
        }
        if(t.isEmpty()){
            logger.error(Markers.ALTERING_LESSON_TABLE_MARKER,"Lesson not added!");
            throw new TeacherNotFoundException("Teacher with id \""+subjId+"\" not found!");
        }

        //перевірка, чи вже існує урок в конкретний час

        return new Object[]{r,s.get(),t.get()};
    }

}
