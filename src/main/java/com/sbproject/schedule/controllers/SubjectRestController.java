package com.sbproject.schedule.controllers;

import com.sbproject.schedule.exceptions.subject.NoSubjectWithSuchIdToDelete;
import com.sbproject.schedule.exceptions.subject.NoSubjectWithSuchIdToUpdate;
import com.sbproject.schedule.models.Specialty;
import com.sbproject.schedule.models.Subject;
import com.sbproject.schedule.models.Teacher;
import com.sbproject.schedule.services.implementations.SpecialtyServiceImpl;
import com.sbproject.schedule.services.implementations.SubjectServiceImpl;
import com.sbproject.schedule.services.implementations.TeacherServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

    /* @GetMapping(value = "/{id}")
    public Foo findById(@PathVariable("id") Long id, HttpServletResponse response) {
        try {
            Foo resourceById = RestPreconditions.checkFound(service.findOne(id));
            eventPublisher.publishEvent(new SingleResourceRetrievedEvent(this, response));
            return resourceById;
        }
        catch (MyResourceNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Foo Not Found", exc);
        }
    } */

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
    public void deleteSubject(@PathVariable("id") Long id) throws NoSubjectWithSuchIdToDelete {
        subjectService.deleteSubject(id);
    }

    @PostMapping("/add")
    public Subject addSubject(@Valid @RequestBody Subject subject){
        return subjectService.addSubject(subject);
    }

    @PutMapping("/update/{id}")
    public Subject updateSubject(@PathVariable("id") Long id, @Valid @RequestBody Subject subject) throws NoSubjectWithSuchIdToUpdate {
        subject.setId(id);
        return subjectService.updateSubject(subject);
    }

    @ExceptionHandler(NoSubjectWithSuchIdToDelete.class)
    public Map<String, String> handleException(NoSubjectWithSuchIdToDelete ex){
        Map<String, String> result = new HashMap<>();
        result.put("deleted", "false");
        result.put("error", ex.getMessage());
        return result;
    }

    @ExceptionHandler(NoSubjectWithSuchIdToUpdate.class)
    public Map<String, String> handleException(NoSubjectWithSuchIdToUpdate ex){
        Map<String, String> result = new HashMap<>();
        result.put("updated", "false");
        result.put("error", ex.getMessage());
        return result;
    }

}
