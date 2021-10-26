package com.sbproject.schedule.controllers;

import com.sbproject.schedule.models.Lesson;
import com.sbproject.schedule.services.interfaces.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
    public Lesson addLesson(@RequestBody Lesson lesson){
        return lessonService.addLesson(lesson);
    }

    @PutMapping("/{id}")
    public Lesson updateLesson(@PathVariable(value = "id") Long id, @RequestBody Lesson lesson) throws Exception{
        lesson.setId(id);
        return lessonService.updateLesson(lesson);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteLesson(@PathVariable(value = "id") Long id) throws Exception{
        lessonService.deleteLesson(id);
        Map<String, Boolean> result = new HashMap<>();
        result.put("deleted",true);
        return result;
    }

}
