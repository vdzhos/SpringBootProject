package com.sbproject.schedule.teacher;


import com.sbproject.schedule.models.Teacher;
import com.sbproject.schedule.repositories.TeacherRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
public class TeacherJPATest {


    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    public void givenTeacherObject_saveInDB_thenTeacherObject() {
        Teacher t = new Teacher("Teacher");
        Teacher t1 = teacherRepository.save(t);

        Assertions.assertEquals(t.getName(), t1.getName());
    }

    @Test
    public void givenName_findTeachers_thenNotEmpty() {
        Teacher t = new Teacher("Teacher");
        teacherRepository.save(t);
        Iterable<Teacher> ts = teacherRepository.findByName("Teacher");
        Assertions.assertTrue(ts.iterator().hasNext());
    }

    @Test
    public void givenIncorrectName_findTeachers_thenNotFound() {
        Teacher t = new Teacher("Teacher");
        teacherRepository.save(t);
        Assertions.assertFalse(teacherRepository.existsByName("Incorrect Name"));
    }

    @Test
    public void givenCorrectName_findTeachers_thenFound() {
        Teacher t = new Teacher("Teacher");
        teacherRepository.save(t);
        Assertions.assertTrue(teacherRepository.existsByName("Teacher"));
    }

}
