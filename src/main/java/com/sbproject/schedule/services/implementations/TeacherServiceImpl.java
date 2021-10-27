package com.sbproject.schedule.services.implementations;

import com.sbproject.schedule.exceptions.teacher.NoTeacherWithSuchIdException;
import com.sbproject.schedule.models.Lesson;
import com.sbproject.schedule.models.Subject;
import com.sbproject.schedule.models.Teacher;
import com.sbproject.schedule.repositories.TeacherRepository;
import com.sbproject.schedule.services.interfaces.TeacherService;
import com.sbproject.schedule.utils.Markers;
import com.sbproject.schedule.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TeacherServiceImpl implements TeacherService {

    private TeacherRepository teacherRepository;
    private static Logger logger = LogManager.getLogger(TeacherServiceImpl.class);

    @Autowired
    private Utils utils;

    @Autowired
    public void setTeacherRepository(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    //TO DO - need to add name check probably
    @Override
    public boolean addTeacher(String name, List<Subject> subjects) {
        teacherRepository.save(new Teacher(name, subjects));
        return true;
    }

    @Override
    public Teacher addTeacher(Teacher teacher) {
        teacher.setId(-1L);
        return teacherRepository.save(teacher);
    }

    @Override
    public boolean teacherExistsById(Long id) {
        return teacherRepository.existsById(id);
    }

    @Override
    public void deleteTeacher(Long id) throws NoTeacherWithSuchIdException{
        if(!teacherExistsById(id)) throw new NoTeacherWithSuchIdException(id, "deleted");
        teacherRepository.deleteById(id);
    }

    @Override
    public boolean updateTeacher(Long id, String name) {
        teacherRepository.save(new Teacher(id, name));
        return true;
    }

    @Override
    public Teacher updateTeacher(Teacher teacher) throws NoTeacherWithSuchIdException {
        if(!teacherExistsById(teacher.getId())) throw new NoTeacherWithSuchIdException(teacher.getId(), "updated");
        return teacherRepository.save(teacher);
    }

    @Override
    public Teacher getTeacherById(Long id) throws Exception{
        return teacherRepository.findById(id).orElseThrow(() -> new Exception("Teacher with id '"+ id +"' not found!"));
    }

    @Override
    public Iterable<Teacher> getAll() {
        return teacherRepository.findAll();
    }

}
