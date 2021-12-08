package com.sbproject.schedule.services.interfaces;

import com.sbproject.schedule.exceptions.teacher.NoTeacherWithSuchIdException;
import com.sbproject.schedule.models.Subject;
import com.sbproject.schedule.models.Teacher;

import java.util.List;
import java.util.Set;


public interface TeacherService {

    Teacher addTeacher(String name, Set<Subject> subjects);
    Teacher addTeacher(Teacher teacher);

    boolean teacherExistsById(Long id);
    boolean teacherExistsByName(String name);

    boolean deleteTeacher(Long id) throws NoTeacherWithSuchIdException;
    boolean updateTeacher(Long id, String name);
    Teacher updateTeacher(Teacher teacher) throws NoTeacherWithSuchIdException;
    Teacher updateTeacherNoCheck(Teacher teacher);
    Teacher getTeacherById(Long id) throws Exception;
    Iterable<Teacher> getTeacherByPartName(String name) throws Exception;
    Iterable<Teacher> getAll();
}
