package com.sbproject.schedule.controllers;

import com.sbproject.schedule.exceptions.lesson.NoLessonWithSuchIdToUpdate;
import com.sbproject.schedule.exceptions.teacher.NoTeacherWithSuchIdException;
import com.sbproject.schedule.models.Teacher;
import com.sbproject.schedule.services.interfaces.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/REST/teachers")
public class TeacherControllerREST {

    private TeacherService teacherService;

    @Autowired
    public TeacherControllerREST(TeacherService teacherService)
    {
        this.teacherService = teacherService;
    }

    @GetMapping("/all")
    public List<Teacher> getAllLessons() {
        return (List<Teacher>) teacherService.getAll();
    }

    @GetMapping("/{id}")
    public Teacher getLessonById(@PathVariable(value = "id") Long id) throws Exception {
        return teacherService.getTeacherById(id);
    }

    @PostMapping("/add")
    public Teacher addTeacher(@Valid @RequestBody Teacher teacher){
        return teacherService.addTeacher(teacher);
    }

    @PutMapping("/{id}")
    public Teacher updateTeacher(@PathVariable(value = "id") Long id, @Valid @RequestBody Teacher teacher) throws NoTeacherWithSuchIdException {
        teacher.setId(id);
        return teacherService.updateTeacher(teacher);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteTeacher(@PathVariable(value = "id") Long id) throws NoTeacherWithSuchIdException {
        teacherService.deleteTeacher(id);
        Map<String, Boolean> result = new HashMap<>();
        result.put("deleted",true);
        return result;
    }

    @ExceptionHandler(NoTeacherWithSuchIdException.class)
    public Map<String, String> handleException(NoTeacherWithSuchIdException ex){
        Map<String, String> result = new HashMap<>();
        result.put(ex.getDelOrUpd(), "false");
        result.put("error", ex.getMessage());
        return result;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleException(MethodArgumentNotValidException ex){
        Map<String, String> result = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            result.put(fieldName, errorMessage);
        });
        return result;
    }
}
