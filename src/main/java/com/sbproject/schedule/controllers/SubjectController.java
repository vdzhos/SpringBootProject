package com.sbproject.schedule.controllers;

import com.sbproject.schedule.models.Specialty;
import com.sbproject.schedule.models.Teacher;
import com.sbproject.schedule.services.implementations.SubjectServiceImpl;
import com.sbproject.schedule.services.interfaces.SubjectService;
import com.sbproject.schedule.utils.Markers;
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

import java.util.List;
import java.util.Set;

/**
 * controls manipulations with subjects
 */
@Controller
@RequestMapping("/subject")
public class SubjectController {

    private SubjectServiceImpl subjectService;
    private static Logger logger = LogManager.getLogger(SubjectController.class);

    private final String REDIRECT_EDIT_PAGE_URL = "/admin";

    @Autowired
    public SubjectController(SubjectServiceImpl subjectService) {
        this.subjectService = subjectService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public RedirectView addSubject(@RequestParam String name, @RequestParam int quantOfGroups,
                             @RequestParam Set<Specialty> specialties, RedirectAttributes redir){
        RedirectView redirectView= new RedirectView(REDIRECT_EDIT_PAGE_URL,true);
        String notification = "Subject '"+name+"' has been successfully added!";
        boolean success = true;
        try {
            subjectService.addSubject(name, quantOfGroups, specialties);
            logger.info(Markers.ALTERING_SUBJECT_TABLE_MARKER,"Subject {} with {} groups has been successfully added!", name, quantOfGroups);
        }
        catch (Exception e) {
            success = false;
            notification = e.getMessage();
            logger.error(Markers.ALTERING_SUBJECT_TABLE_MARKER,notification + " Subject {} with {} groups has not been added!", name, quantOfGroups);
        }
        redir.addFlashAttribute("showNotification", true);
        redir.addFlashAttribute("success", success);
        redir.addFlashAttribute("notification", notification);
        redir.addFlashAttribute("tab",1);
        return redirectView;
    }

    /*@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public RedirectView addSubject(@RequestParam String name, @RequestParam int quantOfGroups, RedirectAttributes redir){
        return addSubject(name, quantOfGroups, null, redir);
    }*/

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete")
    public RedirectView deleteSubject(@RequestParam Long id, @RequestParam String subject, RedirectAttributes redir) {
        ThreadContext.put("subject",subject);
        subjectService.deleteSubject(id);
        ThreadContext.clearAll();
        RedirectView redirectView= new RedirectView(REDIRECT_EDIT_PAGE_URL,true);
        String notification = "Subject has been successfully deleted!";
        logger.info(Markers.DELETE_SUBJECT_MARKER,notification);
        redir.addFlashAttribute("showNotification", true);
        redir.addFlashAttribute("success", true);
        redir.addFlashAttribute("notification",notification);
        redir.addFlashAttribute("tab",1);
        return redirectView;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update")
    public RedirectView updateSubject(@RequestParam Long id, @RequestParam String subjName,
                                      @RequestParam int subjQuantOfGroups,
                                      //@RequestParam Set<Teacher> subjTeachers,
                                      @RequestParam Set<Specialty> subjSpecialties,
                                      RedirectAttributes redir){
        RedirectView redirectView= new RedirectView(REDIRECT_EDIT_PAGE_URL,true);
        String notification = "Subject has been successfully updated!";
        boolean success = true;
        try{
            //subjectService.updateSubject(id, subjName, subjQuantOfGroups, subjTeachers, subjSpecialties);
            subjectService.updateSubject(id, subjName, subjQuantOfGroups, subjSpecialties);
            logger.info(Markers.UPDATE_SUBJECT_MARKER,notification);
        } catch (Exception e) {
            success = false;
            notification = e.getMessage();
            logger.error(Markers.UPDATE_SUBJECT_MARKER,notification + " Subject has not been updated!");
        }
        redir.addFlashAttribute("showNotification", true);
        redir.addFlashAttribute("success", success);
        redir.addFlashAttribute("notification",notification);
        redir.addFlashAttribute("tab",1);
        return redirectView;
    }

}
