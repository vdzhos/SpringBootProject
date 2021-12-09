package com.sbproject.schedule.controllers;

import com.sbproject.schedule.models.Subject;
import com.sbproject.schedule.services.interfaces.TeacherService;
import com.sbproject.schedule.utils.Markers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Set;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    private TeacherService teacherService;
    private static Logger logger = LogManager.getLogger(TeacherController.class);

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public RedirectView addTeacher(@RequestParam String name, @RequestParam Set<Subject> subjects, Model model, RedirectAttributes redir){
        RedirectView redirectView= new RedirectView("/admin",true);
        String notification = "Teacher '"+name+"' has been successfully added!";
        boolean success = true;
        try {
            teacherService.addTeacher(name, subjects);
            logger.info(Markers.ALTERING_TEACHER_TABLE_MARKER,"Teacher {} with {} subjects has been successfully added!", name, subjects);
        }
        catch (Exception e) {
            success = false;
            notification = e.getMessage();
            logger.error(Markers.ALTERING_TEACHER_TABLE_MARKER,"Teacher {} with {} subjects has not been added!", name, subjects);
        }
        redir.addFlashAttribute("success", success);
        redir.addFlashAttribute("notification", notification);
        redir.addFlashAttribute("tab",2);
        return redirectView;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete")
    public RedirectView deleteTeacher(@RequestParam Long id, Model model, RedirectAttributes redir) throws Exception {
        RedirectView redirectView = new RedirectView("/admin",true);
        String name = teacherService.getTeacherById(id).getName();
        String notification = "Teacher '"+ name +"' has been successfully deleted!";
        boolean success =  teacherService.deleteTeacher(id);
        if(success) logger.info(Markers.DELETE_TEACHER_MARKER,"Teacher {} has been successfully deleted!", name);
        else {
            notification = "Teacher has not been deleted!";
            logger.error(Markers.DELETE_TEACHER_MARKER,"Teacher {} has not been deleted!", name);
        }
        redir.addFlashAttribute("success", success);
        redir.addFlashAttribute("notification", notification);
        redir.addFlashAttribute("tab",2);
        return redirectView;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update")
    public RedirectView updateTeacher(@RequestParam Long id, @RequestParam String teacherName,
                                      @RequestParam Set<Subject> teacherSubjects,
                                      RedirectAttributes redir){
        RedirectView redirectView= new RedirectView("/admin",true);
        String notification = "Teacher has been successfully updated!";
        boolean success = true;
        try{
            teacherService.updateTeacher(id, teacherName, teacherSubjects);
            logger.info(Markers.UPDATE_TEACHER_MARKER,notification);
        } catch (Exception e) {
            success = false;
            notification = e.getMessage();
            logger.error(Markers.UPDATE_TEACHER_MARKER,notification + " Teacher has not been updated!");
        }
        redir.addFlashAttribute("showNotification", true);
        redir.addFlashAttribute("success", success);
        redir.addFlashAttribute("notification",notification);
        redir.addFlashAttribute("tab",2);
        return redirectView;
    }

}
