package com.sbproject.schedule.controllers;

import com.sbproject.schedule.exceptions.lesson.NoLessonWithSuchIdFound;
import com.sbproject.schedule.models.Lesson;
import com.sbproject.schedule.models.Room;
import com.sbproject.schedule.models.Subject;
import com.sbproject.schedule.models.SubjectType;
import com.sbproject.schedule.services.interfaces.LessonService;
import com.sbproject.schedule.utils.Markers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String addLesson(@RequestParam int day,      @RequestParam int time,  @RequestParam long subjId,
                            @RequestParam long teachId, @RequestParam int group, @RequestParam String weeks,
                            @RequestParam String room, Model model){
        Room r;
        if(room.equals("remotely")) r = new Room();
        else r = new Room(room);
        boolean result;
        if(weeks.isEmpty()) result = false;
        else{
            result = lessonService.addLesson(Lesson.Time.values()[time],subjId,teachId,new SubjectType(group), weeks, r, DayOfWeek.of(day));
        }
        if(result) logger.info(Markers.ALTERING_LESSON_TABLE_MARKER,"Lesson successfully added!");
        else logger.error(Markers.ALTERING_LESSON_TABLE_MARKER,"Lesson not added!");
        //put info about success/failure into the model
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String deleteLesson(@RequestParam Long id, @RequestParam String lesson, Model model){
        ThreadContext.put("lesson", lesson);
        try {
            lessonService.deleteLesson(id);
        } catch (NoLessonWithSuchIdFound noLessonWithSuchIdFound) {
            noLessonWithSuchIdFound.printStackTrace();
            //print failure message
        }
        ThreadContext.clearAll();
        //put info about success/failure into the model
        return "redirect:/admin";
    }

    @PostMapping("/update")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String updateLesson(@RequestParam Long id, @RequestParam int day, @RequestParam int time,
                               @RequestParam long subjId, @RequestParam long teachId, @RequestParam int group,
                               @RequestParam String weeks, @RequestParam String room, Model model){
        Room r;
        if(room.equals("remotely")) r = new Room();
        else r = new Room(room);
        boolean result = lessonService.updateLesson(id,Lesson.Time.values()[time],subjId,teachId,new SubjectType(group),weeks,r,DayOfWeek.of(day));
        if(result) logger.info(Markers.ALTERING_LESSON_TABLE_MARKER,"Lesson successfully updated!");
        else logger.error(Markers.ALTERING_LESSON_TABLE_MARKER,"Lesson not updated!");
        return "redirect:/admin";
    }

}
