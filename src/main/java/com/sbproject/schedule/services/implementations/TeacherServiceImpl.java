package com.sbproject.schedule.services.implementations;

import com.sbproject.schedule.models.Subject;
import com.sbproject.schedule.models.Teacher;
import com.sbproject.schedule.repositories.TeacherRepository;
import com.sbproject.schedule.services.interfaces.TeacherService;
import com.sbproject.schedule.utils.Markers;
import com.sbproject.schedule.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    public boolean addTeacher(String name, Set<Subject> subjects) {
        teacherRepository.save(new Teacher(name, subjects));
        return true;
    }

    //TO DO - need to add name check probably
    @Override
    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
        logger.info(Markers.DELETE_TEACHER_MARKER,"Teacher has been successfully deleted!");
    }

    @Override
    public boolean updateTeacher(Long id, String name) {
        teacherRepository.save(new Teacher(id, name));
        return true;
    }

    @Override
    public Iterable<Teacher> getAll() {
        return teacherRepository.findAll();
    }

}
