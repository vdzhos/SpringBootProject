package com.sbproject.schedule.controllers;

import com.sbproject.schedule.models.Lesson;
import com.sbproject.schedule.models.Schedule;
import com.sbproject.schedule.services.interfaces.LessonService;
import com.sbproject.schedule.services.interfaces.SpecialtyService;
import com.sbproject.schedule.services.interfaces.SubjectService;
import com.sbproject.schedule.services.interfaces.TeacherService;
import com.sbproject.schedule.services.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Collections;
import java.util.List;

/**
 * loads all the data in all the tabs on the settings page
 */

@Controller
public class MainController {

    @Autowired
    private SpecialtyService specialtyService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private LessonService lessonService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private ScheduleReaderSaverService readerSaverService;

    @Value("${spring.application.name}")
    private String appName;

    @Value("${custom.file-url}")
    private String fileUrl;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String getAllAdmin(Model model){
        model.addAttribute("appName",appName);
        model.addAttribute("specialties",specialtyService.getAll());
        model.addAttribute("subjects",subjectService.getAll());
        model.addAttribute("teachers", teacherService.getAll());
        if(model.getAttribute("tab") == null){
            model.addAttribute("tab",0);
        }
        if(model.getAttribute("schedule") == null){
            model.addAttribute("schedule", new Schedule(Collections.emptyList()));
        }
        if(model.getAttribute("lessonSpec") == null){
            model.addAttribute("lessonSpec", -1);
        }

        return "editSchedule";
    }

    @PreAuthorize("hasAnyRole('REGULAR', 'ADMIN')")
    @GetMapping
    public String showAllSchedules(Model model,Authentication authentication){
        model.addAttribute("appName",appName);
        model.addAttribute("specialties",specialtyService.getAll());
        boolean adminLoggedIn = false;
        if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            adminLoggedIn = true;
        }
        model.addAttribute("adminLoggedIn",adminLoggedIn);
        model.addAttribute("lessons",(List<Lesson>)lessonService.getAll());
        model.addAttribute("teachers", teacherService.getAll());
        return "mainPage";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/uploadSchedule")
    public RedirectView uploadSchedule(@RequestParam("file") MultipartFile file, @RequestParam(required = false) Long specialtyId, RedirectAttributes redir) throws Exception {
        RedirectView redirectView= new RedirectView("/",true);
        String notification = "The schedule has been successfully uploaded!";
        boolean success = true;
        try {
            readerSaverService.readSaveSchedule(file.getInputStream(),specialtyId);
        }catch (Exception e){
            success = false;
            notification = e.getMessage();
        }
        redir.addFlashAttribute("showNotification", true);
        redir.addFlashAttribute("success", success);
        redir.addFlashAttribute("notification",notification);
        return redirectView;
    }

}
