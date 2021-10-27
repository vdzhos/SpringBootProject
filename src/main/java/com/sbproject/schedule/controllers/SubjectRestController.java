package com.sbproject.schedule.controllers;

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

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/subjects")
public class SubjectRestController {

    @Autowired
    private SubjectServiceImpl subjectService;
    @Autowired
    private SpecialtyServiceImpl specialtyService;
    @Autowired
    private TeacherServiceImpl teacherService;
    private static Logger logger = LogManager.getLogger(SubjectRestController.class);


    //private static final String template = "Hello, %s!";
    //private final AtomicLong counter = new AtomicLong();

    /*@GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        Greeting gr = new Greeting(counter.incrementAndGet(), String.format(template, name));
        System.out.println(gr.getContent());
        return gr;
    }*/

    //@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    //@GetMapping("/get/{id}")

    //@GetMapping
    //@PostMapping
    //@PutMapping
    //@DeleteMapping
    //@PatchMapping

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
    public void deleteSubject(@PathVariable("id") Long id) throws Exception{
        subjectService.deleteSubject(id);
    }

    @PostMapping("/post")
    public Subject addSubject(@RequestBody Subject subject){
        //Set<Specialty> specialties = specialtyService.getSpecialtiesByIds(specialtiesIds);
        return subjectService.addSubject(subject);
    }

    @PutMapping("/put/{id}")
    public Subject updateSubject(@PathVariable("id") Long id, @RequestBody Subject subject) throws Exception{
        // Set<Specialty> specialties = specialtyService.getSpecialtiesByIds(specialtiesIds);
        // Set<Teacher> teachers = teacherService.getTeachersByIds(teachersIds);
        subject.setId(id);
        return subjectService.updateSubject(subject);
    }

    /* @PostMapping("/post")
    public void addSubject(@RequestBody String name, @RequestBody int quantOfGroups, @RequestBody List<Long> specialtiesIds){
        Set<Specialty> specialties = specialtyService.getSpecialtiesByIds(specialtiesIds);
        subjectService.addSubject(name, quantOfGroups, specialties);
        //return subjectService.getSubjectByName(name);
    }

    @PutMapping("/put/{id}")
    public void updateSubject(@PathVariable("id") Long id, @RequestBody String name, @RequestBody int quantOfGroups, @RequestBody List<Long> teachersIds, @RequestBody List<Long> specialtiesIds){
        Set<Specialty> specialties = specialtyService.getSpecialtiesByIds(specialtiesIds);
        Set<Teacher> teachers = teacherService.getTeachersByIds(teachersIds);
        subjectService.updateSubject(id, name, quantOfGroups, teachers, specialties);
        //return subjectService.getSubjectByName(name);
    } */

}
