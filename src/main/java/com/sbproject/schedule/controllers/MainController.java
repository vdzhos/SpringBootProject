package com.sbproject.schedule.controllers;

import com.sbproject.schedule.services.implementations.SpecialtyServiceImpl;
import com.sbproject.schedule.services.implementations.SubjectServiceImpl;
import com.sbproject.schedule.services.interfaces.LessonService;
import com.sbproject.schedule.services.interfaces.SpecialtyService;
import com.sbproject.schedule.services.interfaces.SubjectService;
import com.sbproject.schedule.services.interfaces.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * loads all the data in all the tabs on the settings page
 */

@Controller
//I assume that main page is accessible only for admins
//@PreAuthorize("hasAnyRole('ADMIN')")
public class MainController {

    @Autowired
    private SpecialtyService specialtyService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private LessonService lessonService;
    @Autowired
    private TeacherService teacherService;

    @Value("${spring.application.name}")
    private String appName;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String getAllAdmin(Model model){
        model.addAttribute("appName",appName);
        model.addAttribute("specialties",specialtyService.getAll());
        model.addAttribute("subjects",subjectService.getAll());
        model.addAttribute("lessons", lessonService.getAll());
        model.addAttribute("teachers", teacherService.getAll());
        return "editSchedule";
    }

//    @PreAuthorize("hasRole('REGULAR')")
    @GetMapping
    public String showAllSchedules(Model model,Authentication authentication){
        model.addAttribute("appName",appName);
        boolean adminLoggedIn = false;
        if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            adminLoggedIn = true;
        }
        model.addAttribute("adminLoggedIn",adminLoggedIn);
        return "mainPage";
    }


//    @GetMapping
//    public String loginPage(Authentication authentication) {
//        if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
//            return "redirect:/admin";
//        } else if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_REGULAR"))) {
//            return "redirect:/user";
//        }
//        return "login";
//    }

}
