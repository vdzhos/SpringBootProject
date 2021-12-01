package com.sbproject.schedule.lesson;

import com.sbproject.schedule.models.*;
import com.sbproject.schedule.repositories.LessonRepository;
import com.sbproject.schedule.repositories.SubjectRepository;
import com.sbproject.schedule.repositories.TeacherRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.DayOfWeek;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class LessonJPATest {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    public void givenLessonObject_saveInDB_thenLessonObject() {
        Subject s = new Subject("Subject 1", 3);
        Teacher t = new Teacher("Teacher 1");

        Subject s1 = subjectRepository.save(s);
        Teacher t1 = teacherRepository.save(t);

        Lesson l = new Lesson(Lesson.Time.TIME1, s1, t1, new SubjectType(0), "1-15", new Room("215"), DayOfWeek.MONDAY);
        Lesson l1 = lessonRepository.save(l);

        Assertions.assertEquals(l.getTime(), l1.getTime());
        Assertions.assertEquals(l.getSubject(), l1.getSubject());
        Assertions.assertEquals(l.getTeacher(), l1.getTeacher());
        Assertions.assertEquals(l.getGroup(), l1.getGroup());
        Assertions.assertEquals(l.getWeeks(), l1.getWeeks());
        Assertions.assertEquals(l.getRoom(), l1.getRoom());
        Assertions.assertEquals(l.getDayOfWeek(), l1.getDayOfWeek());
    }

    @Test
    public void givenLessonObjects_findAllObjects_thenIterableContains()
    {
        Subject s = new Subject("Subject 1", 3);
        Teacher t = new Teacher("Teacher 1");

        Subject s1 = subjectRepository.save(s);
        Teacher t1 = teacherRepository.save(t);

        Lesson l = new Lesson(Lesson.Time.TIME1, s1, t1, new SubjectType(0), "1-15", new Room("215"), DayOfWeek.MONDAY);
        Lesson l1 = lessonRepository.save(l);

        Subject ss = new Subject("Subject 2", 3);
        Teacher tt = new Teacher("Teacher 2");

        Subject ss1 = subjectRepository.save(ss);
        Teacher tt1 = teacherRepository.save(tt);

        Lesson ll = new Lesson(Lesson.Time.TIME1, ss1, tt1, new SubjectType(0), "1-15", new Room("215"), DayOfWeek.MONDAY);
        Lesson ll1 = lessonRepository.save(ll);

        assertThat(lessonRepository.findAll()).hasSize(2).contains(l1,ll1);
    }

    @Test
    public void givenLessonObjects_deleteById_thenIterableNotContain()
    {
        Subject s = new Subject("Subject 1", 3);
        Teacher t = new Teacher("Teacher 1");

        Subject s1 = subjectRepository.save(s);
        Teacher t1 = teacherRepository.save(t);

        Lesson l = new Lesson(Lesson.Time.TIME1, s1, t1, new SubjectType(0), "1-15", new Room("215"), DayOfWeek.MONDAY);
        Lesson l1 = lessonRepository.save(l);

        Subject ss = new Subject("Subject 2", 3);
        Teacher tt = new Teacher("Teacher 2");

        Subject ss1 = subjectRepository.save(ss);
        Teacher tt1 = teacherRepository.save(tt);

        Lesson ll = new Lesson(Lesson.Time.TIME1, ss1, tt1, new SubjectType(0), "1-15", new Room("215"), DayOfWeek.MONDAY);
        Lesson ll1 = lessonRepository.save(ll);

        lessonRepository.deleteById(l1.getId());
        assertThat(lessonRepository.findAll()).hasSize(1).contains(ll1);
    }


    @Test
    public void givenLessonObjects_deleteAll_thenIterableIsEmpty()
    {
        Subject s = new Subject("Subject 1", 3);
        Teacher t = new Teacher("Teacher 1");

        Subject s1 = subjectRepository.save(s);
        Teacher t1 = teacherRepository.save(t);

        Lesson l = new Lesson(Lesson.Time.TIME1, s1, t1, new SubjectType(0), "1-15", new Room("215"), DayOfWeek.MONDAY);
        lessonRepository.save(l);

        Subject ss = new Subject("Subject 2", 3);
        Teacher tt = new Teacher("Teacher 2");

        Subject ss1 = subjectRepository.save(ss);
        Teacher tt1 = teacherRepository.save(tt);

        Lesson ll = new Lesson(Lesson.Time.TIME1, ss1, tt1, new SubjectType(0), "1-15", new Room("215"), DayOfWeek.MONDAY);
        lessonRepository.save(ll);

        lessonRepository.deleteAll();
        assertThat(lessonRepository.findAll()).isEmpty();
    }

}
