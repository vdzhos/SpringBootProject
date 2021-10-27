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

    @PostMapping("/add")
    public RedirectView addTeacher(@RequestParam String name, @RequestParam List<Subject> subjects, Model model, RedirectAttributes redir){
        RedirectView redirectView= new RedirectView("/",true);
        String notification = "Вчитель '"+name+"' був успішно доданий!";
        boolean success =  teacherService.addTeacher(name, subjects);
        if(success) logger.info(Markers.ALTERING_TEACHER_TABLE_MARKER,"Teacher {} with {} subjects has been successfully added!", name, subjects);
        else {
            notification = "Вчитель не був доданий!";
            logger.error(Markers.ALTERING_TEACHER_TABLE_MARKER,"Teacher {} with {} subjects has not been added!", name, subjects);
        }
        redir.addFlashAttribute("success", success);
        redir.addFlashAttribute("notification", notification);
        return redirectView;
    }

    @PostMapping("/delete")
    public String deleteTeacher(@RequestParam Long id, @RequestParam String teacherToString, Model model) throws NoTeacherWithSuchIdException {
        ThreadContext.put("teacher",teacherToString);
        teacherService.deleteTeacher(id);
        ThreadContext.clearAll();
        logger.info(Markers.DELETE_TEACHER_MARKER,"Teacher has been successfully deleted!");
        //put info about success/failure into the model
        return "redirect:/";
    }

    @PostMapping("/update")
    public String updateTeacher(/*@RequestParam Long id, @RequestParam String newName, */Model model){
        logger.info(Markers.ALTERING_TEACHER_TABLE_MARKER,"Teacher has been successfully updated!");
        //teacherService.updateTeacher(id, newName);
        //put info about success/failure into the model
        return "redirect:/";
    }
}
