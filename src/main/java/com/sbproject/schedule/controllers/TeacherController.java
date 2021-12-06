package com.sbproject.schedule.controllers;

import com.sbproject.schedule.exceptions.teacher.NoTeacherWithSuchIdException;
import com.sbproject.schedule.models.Subject;
import com.sbproject.schedule.services.implementations.TeacherServiceImpl;
import com.sbproject.schedule.services.interfaces.LessonService;
import com.sbproject.schedule.services.interfaces.TeacherService;
import com.sbproject.schedule.utils.Markers;
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

import java.util.List;
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
    public RedirectView addTeacher(@RequestParam String name, @RequestParam List<Subject> subjects, Model model, RedirectAttributes redir){
        RedirectView redirectView= new RedirectView("/admin",true);
        String notification = "Teacher '"+name+"' has been successfully added!";
        boolean success =  teacherService.addTeacher(name, subjects);
        if(success) logger.info(Markers.ALTERING_TEACHER_TABLE_MARKER,"Teacher {} with {} subjects has been successfully added!", name, subjects);
        else {
            notification = "Teacher has not been added!";
            logger.error(Markers.ALTERING_TEACHER_TABLE_MARKER,"Teacher {} with {} subjects has not been added!", name, subjects);
        }
        redir.addFlashAttribute("success", success);
        redir.addFlashAttribute("notification", notification);
        redir.addFlashAttribute("tab",2);
        return redirectView;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete")
    public RedirectView deleteTeacher(@RequestParam Long id, @RequestParam String teacherToString, Model model, RedirectAttributes redir) throws Exception {
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
    public String updateTeacher(/*@RequestParam Long id, @RequestParam String newName, */Model model){
        logger.info(Markers.ALTERING_TEACHER_TABLE_MARKER,"Teacher has been successfully updated!");
        //teacherService.updateTeacher(id, newName);
        //put info about success/failure into the model
        return "redirect:/";
    }


}
