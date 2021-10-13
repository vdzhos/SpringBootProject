package com.sbproject.schedule.services.implementations;

import com.sbproject.schedule.models.Teacher;
import com.sbproject.schedule.repositories.TeacherRepository;
import com.sbproject.schedule.services.interfaces.TeacherService;
import com.sbproject.schedule.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService {

    private TeacherRepository teacherRepository;

    @Autowired
    private Utils utils;

    @Autowired
    public void setTeacherRepository(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    //TO DO - need to add name check probably
    @Override
    public boolean addTeacher(String name) {
        teacherRepository.save(new Teacher(utils.getUniqueId(), name));
        return true;
    }

    //TO DO - need to add name check probably
    @Override
    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
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
