package com.sbproject.schedule.controllers;

import com.sbproject.schedule.exceptions.specialty.SpecialtyNotFoundException;
import com.sbproject.schedule.exceptions.subject.NoSubjectWithSuchIdToDelete;
import com.sbproject.schedule.exceptions.subject.NoSubjectWithSuchIdToUpdate;
import com.sbproject.schedule.exceptions.subject.SubjectNotFoundException;
import com.sbproject.schedule.models.Specialty;
import com.sbproject.schedule.models.Subject;
import com.sbproject.schedule.models.Teacher;
import com.sbproject.schedule.services.implementations.SpecialtyServiceImpl;
import com.sbproject.schedule.services.implementations.SubjectServiceImpl;
import com.sbproject.schedule.services.implementations.TeacherServiceImpl;
import com.sbproject.schedule.utils.Markers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/subjects")
public class SubjectRestController {

    @Autowired
    private SubjectServiceImpl subjectService;
    private static Logger logger = LogManager.getLogger(SubjectRestController.class);

    @GetMapping("/get")
    public Iterable<Subject> getSubjects(){
        return subjectService.getAll();
    }

    @GetMapping("/getByName/{name}")
    public Subject getSubject(@PathVariable("name") String name){
        return subjectService.getSubjectByName(name);
    }

    @GetMapping("/get/{id}")
    public Subject getSubject(@PathVariable("id") Long id) throws Exception{
        return subjectService.getSubjectById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteSubject(@PathVariable("id") Long id) {
        subjectService.deleteSubject(id);
        logger.info(Markers.DELETE_SUBJECT_MARKER,"Subject has been successfully deleted!");
    }

    @PostMapping("/add")
    public Subject addSubject(@Valid @RequestBody Subject subject){
        Subject s = subjectService.addSubject(subject);
        logger.info(Markers.ALTERING_SUBJECT_TABLE_MARKER,"Subject {} with {} groups has been successfully added!", subject.getName(), subject.getQuantOfGroups());
        return s;
    }

    @PutMapping("/update/{id}")
    public Subject updateSubject(@PathVariable("id") Long id, @Valid @RequestBody Subject subject) {
        subject.setId(id);
        Subject s = subjectService.updateSubject(subject);
        logger.info(Markers.UPDATE_SUBJECT_MARKER,"Subject has been successfully deleted!");
        return s;
    }

    @ExceptionHandler(NoSubjectWithSuchIdToDelete.class)
    public ResponseEntity<Map<String,String>> handleException(NoSubjectWithSuchIdToDelete ex){
        Map<String, String> result = new HashMap<>();
        result.put("deleted", "false");
        result.put("error", ex.getMessage());
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSubjectWithSuchIdToUpdate.class)
    public ResponseEntity<Map<String,String>> handleException(NoSubjectWithSuchIdToUpdate ex){
        Map<String, String> result = new HashMap<>();
        result.put("updated", "false");
        result.put("error", ex.getMessage());
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }


}
