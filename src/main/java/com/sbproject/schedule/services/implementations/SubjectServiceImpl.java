package com.sbproject.schedule.services.implementations;

import com.sbproject.schedule.database.Database;
import com.sbproject.schedule.models.Subject;
import com.sbproject.schedule.models.Teacher;
import com.sbproject.schedule.repositories.fakes.interfaces.SubjectRepository;
import com.sbproject.schedule.services.interfaces.SubjectService;
import com.sbproject.schedule.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {

    private SubjectRepository subjectRepository;
    private Utils processor;

    @Autowired
    public void setProcessor(Utils processor) {
        this.processor = processor;
    }

    @Autowired
    public void setSubjectRepository(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public boolean addSubject(String name, int quantOfGroups, List<Teacher> teachers) {
        if(subjectRepository.existsByNameAndQuantOfGroups(name, quantOfGroups))
            return false;
        subjectRepository.save(new Subject(Database.getUniqueId(), processor.processName(name), quantOfGroups, teachers));
        return true;
    }

    @Override
    public void deleteSubject(Long id) {
        subjectRepository.deleteById(id);
    }

    @Override
    public boolean updateSubject(String name, int quantOfGroups, List<Teacher> teachers) {
        subjectRepository.save(new Subject(Database.getUniqueId(), processor.processName(name), quantOfGroups, teachers));
        return false;
    }

    @Override
    public Iterable<Subject> getAll() {
        return subjectRepository.findAll();
    }

}
