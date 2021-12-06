package com.sbproject.schedule.controllers;

import com.sbproject.schedule.exceptions.lesson.NoLessonWithSuchIdFound;
import com.sbproject.schedule.models.Lesson;
import com.sbproject.schedule.models.SubjectType;
import com.sbproject.schedule.services.interfaces.LessonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.time.DayOfWeek;

@Controller
@RequestMapping("/lesson")
public class LessonController {

    private LessonService lessonService;

    private static Logger logger = LogManager.getLogger(LessonController.class);

    @Autowired
    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public RedirectView addLesson(@RequestParam int day,      @RequestParam int time,  @RequestParam long subjId,
                            @RequestParam long teachId, @RequestParam int group, @RequestParam String weeks,
                            @RequestParam String room, Model model, RedirectAttributes redirect){
        RedirectView redirectView = new RedirectView("/admin",true);
        boolean success = true;
        String notification = "New lesson has been successfully added!";
        try {
            lessonService.addLesson(Lesson.Time.values()[time],subjId,teachId,new SubjectType(group), weeks, room, DayOfWeek.of(day));
        } catch (Exception e) {
            success = false;
            notification = e.getMessage();
        }
        redirect.addFlashAttribute("showNotification", true);
        redirect.addFlashAttribute("success", success);
        redirect.addFlashAttribute("notification",notification);
        redirect.addFlashAttribute("tab",3);
        return redirectView;
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public RedirectView deleteLesson(@RequestParam Long id, @RequestParam String lesson, Model model, RedirectAttributes redirect){
        RedirectView redirectView = new RedirectView("/admin",true);
        boolean success = true;
        String notification = "Lesson has been successfully deleted!";
        ThreadContext.put("lesson", lesson);
        try {
            lessonService.deleteLesson(id);
        } catch (NoLessonWithSuchIdFound e) {
            success = false;
            notification = e.getMessage();
        }
        ThreadContext.clearAll();
        redirect.addFlashAttribute("showNotification", true);
        redirect.addFlashAttribute("success", success);
        redirect.addFlashAttribute("notification",notification);
        redirect.addFlashAttribute("tab",3);
        return redirectView;
    }

    @PostMapping("/update")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public RedirectView updateLesson(@RequestParam Long id, @RequestParam int day, @RequestParam int time,
                               @RequestParam long subjId, @RequestParam long teachId, @RequestParam int group,
                               @RequestParam String weeks, @RequestParam String room, Model model, RedirectAttributes redirect){
        RedirectView redirectView = new RedirectView("/admin",true);
        boolean success = true;
        String notification = "Lesson has been successfully updated!";
        try {
            lessonService.updateLesson(id,Lesson.Time.values()[time],subjId,teachId,new SubjectType(group),weeks,room,DayOfWeek.of(day));
        } catch (Exception e) {
            success = false;
            notification = e.getMessage();
        }
        redirect.addFlashAttribute("showNotification", true);
        redirect.addFlashAttribute("success", success);
        redirect.addFlashAttribute("notification",notification);
        redirect.addFlashAttribute("tab",3);
        return redirectView;
    }

}
