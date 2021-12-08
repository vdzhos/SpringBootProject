package com.sbproject.schedule.controllers;

import com.sbproject.schedule.exceptions.lesson.NoLessonWithSuchIdFound;
import com.sbproject.schedule.models.*;
import com.sbproject.schedule.services.interfaces.LessonService;
import com.sbproject.schedule.services.interfaces.SpecialtyService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping("/lesson")
public class LessonController {

    private LessonService lessonService;
    private SpecialtyService specialtyService;

    private static Logger logger = LogManager.getLogger(LessonController.class);

    @Autowired
    public LessonController(LessonService lessonService, SpecialtyService specialtyService) {
        this.lessonService = lessonService;
        this.specialtyService = specialtyService;
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public RedirectView addLesson(@RequestParam int day,      @RequestParam int time,  @RequestParam long subjId,
                            @RequestParam long teachId, @RequestParam int group, @RequestParam String weeks,
                            @RequestParam String room, @RequestParam Long specId, Model model, RedirectAttributes redirect){
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

        redirect.addFlashAttribute("schedule", new Schedule((List<Lesson>) getLessons(specId,-1L)));
        redirect.addFlashAttribute("lessonSpec",specId);

        return redirectView;
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public RedirectView deleteLesson(@RequestParam Long id, @RequestParam String lesson, @RequestParam Long specId, Model model, RedirectAttributes redirect){
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

        redirect.addFlashAttribute("schedule", new Schedule((List<Lesson>) getLessons(specId,-1L)));
        redirect.addFlashAttribute("lessonSpec",specId);

        return redirectView;
    }

    @PostMapping("/update")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public RedirectView updateLesson(@RequestParam Long id, @RequestParam int day, @RequestParam int time,
                               @RequestParam long subjId, @RequestParam long teachId, @RequestParam int group,
                               @RequestParam String weeks, @RequestParam String room, @RequestParam Long specId, Model model, RedirectAttributes redirect){
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

        redirect.addFlashAttribute("schedule", new Schedule((List<Lesson>) getLessons(specId,id)));
        redirect.addFlashAttribute("lessonSpec",specId);

        return redirectView;
    }

    @GetMapping("/getBySpecialtyId")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public RedirectView getLessonsBySpecialtyId(@RequestParam Long specId, Model model, RedirectAttributes redirect){
        RedirectView redirectView = new RedirectView("/admin",true);

        redirect.addFlashAttribute("schedule", new Schedule((List<Lesson>) getLessons(specId,-1L)));
        redirect.addFlashAttribute("lessonSpec",specId);
        redirect.addFlashAttribute("tab",3);

        return redirectView;
    }

    private Iterable<Lesson> getLessons(Long specId, Long deleteUpdated){
        List<Lesson> lessons;
        if(specId == -1){
            lessons = Collections.emptyList();
        }else{
            Iterable<Subject> subjects = specialtyService.getSpecialty(specId).getSubjects();
            lessons = new ArrayList<>();
            StreamSupport.stream(subjects.spliterator(), false).forEach(subj -> lessons.addAll(subj.getLessons()));
            if(deleteUpdated!=-1){
                List<Long> subjIds = new ArrayList<>();
                subjects.forEach(it -> subjIds.add(it.getId()));
                for (int i = 0; i < lessons.size(); i++) {
                    if(lessons.get(i).getId().equals(deleteUpdated)){
                        if(!subjIds.contains(lessons.get(i).getSubject().getId())) lessons.remove(i);
                        else break;
                    }
                }
            }
            Collections.sort(lessons);
        }
        return lessons;
    }

}
