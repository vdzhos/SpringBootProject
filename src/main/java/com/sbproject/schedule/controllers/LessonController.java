package com.sbproject.schedule.controllers;

import com.sbproject.schedule.services.interfaces.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/lesson")
public class LessonController {

    private LessonService lessonService;

    @Autowired
    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @PostMapping("/add")
    public String addLesson(Model model){
//        lessonService.addLesson();
        //put info about success/failure into the model
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String deleteLesson(@RequestParam Long id, Model model){
        lessonService.deleteLesson(id);
        //put info about success/failure into the model
        return "redirect:/";
    }

    @PostMapping("/update")
    public String updateSpecialty(Model model){
//        lessonService.updateLesson();
        //put info about success/failure into the model
        return "redirect:/";
    }

}
