package com.sbproject.schedule.services.implementations;

import com.sbproject.schedule.exceptions.subject.NoSubjectWithSuchIdToDelete;
import com.sbproject.schedule.exceptions.subject.SubjectNotFoundException;
import com.sbproject.schedule.models.Specialty;
import com.sbproject.schedule.models.Subject;
import com.sbproject.schedule.repositories.SubjectRepository;
import com.sbproject.schedule.services.interfaces.SubjectService;
import com.sbproject.schedule.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    private Utils processor;

    @Autowired
    public void setProcessor(Utils processor) {
        this.processor = processor;
    }

    @Override
    public Subject addSubject(String name, int quantOfGroups, Set<Specialty> specialties) {
        name = processor.processName(name);
        processor.checkSubjectName(name);
        processor.checkQuantOfGroups(quantOfGroups);
        processor.checkQuantOfSpecialties(specialties == null? 0 : specialties.size());
        Iterable<Subject> subjectsWithSuchName = subjectRepository.findByName(name);
        processor.checkSpecialties(subjectsWithSuchName, specialties);
        return subjectRepository.save(new Subject(name, quantOfGroups, specialties));
    }

    @Override
    public Subject addSubject(Subject subject) {
        subject.setId(-1L);
        //return subjectRepository.save(subject);
        return addSubject(subject.getName(), subject.getQuantOfGroups(), subject.getSpecialties());
    }

    @Transactional
    @Override
    public void deleteSubject(Long id) {
        if(!subjectExistsById(id)) throw new NoSubjectWithSuchIdToDelete(id);
        subjectRepository.deleteById(id);
    }

    @Override
    public Subject updateSubject(Long id, String name, int quantOfGroups,
                                 //Set<Teacher> teachers,
                                 Set<Specialty> specialties) {
        name = processor.processName(name);
        processor.checkSubjectName(name);
        processor.checkQuantOfGroups(quantOfGroups);
        processor.checkQuantOfSpecialties(specialties == null? 0 : specialties.size());
        Iterable<Subject> subjectsWithSuchName = subjectRepository.findByNameAndNotId(id, name);
        processor.checkSpecialties(subjectsWithSuchName, specialties);
        String finalName = name;
        return subjectRepository.findById(id).map((subject) -> {
            if (nothingChanged(subject, finalName, quantOfGroups, specialties))
                return subject;
            subject.setName(finalName);
            subject.setQuantOfGroups(quantOfGroups);
            //subject.setTeachers(teachers);
            subject.setSpecialties(specialties);
            return subjectRepository.save(subject);
        }).orElseGet(() -> {
            //return subjectRepository.save(new Subject(id, finalName, quantOfGroups, teachers, specialties));
            return subjectRepository.save(new Subject(id, finalName, quantOfGroups, specialties));
        });
    }

    @Override
    public Subject updateSubject(Subject subject) {
        //if(!subjectExistsById(subject.getId())) throw new NoSubjectWithSuchIdToUpdate(subject.getId());
        //return subjectRepository.save(subject);
        //return updateSubject(subject.getId(), subject.getName(), subject.getQuantOfGroups(), subject.getTeachers(), subject.getSpecialties());
        return updateSubject(subject.getId(), subject.getName(), subject.getQuantOfGroups(), subject.getSpecialties());
    }

    @Override
    public Subject updateSubjectNoCheck(Subject subject) {
        return subjectRepository.save(subject);
    }

    @Override
    public Iterable<Subject> getAll() {
        return subjectRepository.findAll();
    }


    @Transactional
    public String countTeachers(long subjectId){
        return subjectRepository.findById(subjectId).get().getTeachers().toString();
    }

    @Override
    public Subject getSubjectByName(String name) {
        Iterable<Subject> res = subjectRepository.findByName(name);
        if (!res.iterator().hasNext()) throw new SubjectNotFoundException("Subject with name '"+ name +"' has not been found!");
        return res.iterator().next();
    }

    @Override
    public Subject getSubjectById(Long id) {
        return subjectRepository.findById(id).orElseThrow(() -> new SubjectNotFoundException("Subject with id '"+ id +"' has not been found!"));
    }

    @Override
    public boolean subjectExistsById(Long id) {
        return subjectRepository.existsById(id);
    }

    @Override
    public boolean subjectExistsByName(String name) {
        return subjectRepository.existsByName(name);
    }

    private boolean nothingChanged(Subject subject, String name, int quantOfGroups, Set<Specialty> specialties) {
        return subject.getName().equals(name) && subject.getQuantOfGroups() == quantOfGroups
                && subject.getSpecialties().equals(specialties);
    }

}
