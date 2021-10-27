package com.sbproject.schedule.controllers;

import com.sbproject.schedule.exceptions.lesson.NoLessonWithSuchIdToDelete;
import com.sbproject.schedule.exceptions.lesson.NoLessonWithSuchIdToUpdate;
import com.sbproject.schedule.models.Lesson;
import com.sbproject.schedule.services.interfaces.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/REST/lessons")
public class LessonControllerREST {

    private LessonService lessonService;

    @Autowired
    public LessonControllerREST(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping("/all")
    public List<Lesson> getAllLessons() {
        return (List<Lesson>) lessonService.getAll();
    }

    @GetMapping("/{id}")
    public Lesson getLessonById(@PathVariable(value = "id") Long id) throws Exception {
        return lessonService.getLessonById(id);
    }

    @PostMapping("/add")
    public Lesson addLesson(@Valid @RequestBody Lesson lesson){
        return lessonService.addLesson(lesson);
    }

    @PutMapping("/{id}")
    public Lesson updateLesson(@PathVariable(value = "id") Long id, @Valid @RequestBody Lesson lesson) throws NoLessonWithSuchIdToUpdate {
        lesson.setId(id);
        return lessonService.updateLesson(lesson);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteLesson(@PathVariable(value = "id") Long id) throws NoLessonWithSuchIdToDelete {
        lessonService.deleteLesson(id);
        Map<String, Boolean> result = new HashMap<>();
        result.put("deleted",true);
        return result;
    }

    @ExceptionHandler(NoLessonWithSuchIdToDelete.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Map<String, String> handleException(NoLessonWithSuchIdToDelete ex){
        Map<String, String> result = new HashMap<>();
        result.put("deleted", "false");
        result.put("error", ex.getMessage());
        return result;
    }

    @ExceptionHandler(NoLessonWithSuchIdToUpdate.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Map<String, String> handleException(NoLessonWithSuchIdToUpdate ex){
        Map<String, String> result = new HashMap<>();
        result.put("updated", "false");
        result.put("error", ex.getMessage());
        return result;
    }

}
