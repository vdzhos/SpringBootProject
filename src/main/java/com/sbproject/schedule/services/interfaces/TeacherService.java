package com.sbproject.schedule.services.interfaces;

import com.sbproject.schedule.exceptions.teacher.NoTeacherWithSuchIdException;
import com.sbproject.schedule.models.Subject;
import com.sbproject.schedule.models.Teacher;

import java.util.List;


public interface TeacherService {

    boolean addTeacher(String name, List<Subject> subjects);
    Teacher addTeacher(Teacher teacher);

    boolean teacherExistsById(Long id);

    boolean deleteTeacher(Long id) throws NoTeacherWithSuchIdException;
    boolean updateTeacher(Long id, String name);
    Teacher updateTeacher(Teacher teacher) throws NoTeacherWithSuchIdException;
    Teacher getTeacherById(Long id) throws Exception;
    Iterable<Teacher> getAll();
}
