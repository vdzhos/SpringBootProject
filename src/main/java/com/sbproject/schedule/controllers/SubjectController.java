package com.sbproject.schedule.controllers;

import com.sbproject.schedule.models.Specialty;
import com.sbproject.schedule.models.Teacher;
import com.sbproject.schedule.services.implementations.SubjectServiceImpl;
import com.sbproject.schedule.services.interfaces.SubjectService;
import com.sbproject.schedule.utils.Markers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
                             @RequestParam Set<Specialty> specialties, Model model, RedirectAttributes redir){
        //put info about success/failure into the model
        //return "redirect:/";
        RedirectView redirectView= new RedirectView(REDIRECT_EDIT_PAGE_URL,true);
        String notification = "Предмет '"+name+"' було успішно додано!";
        boolean success = subjectService.addSubject(name, quantOfGroups, specialties);
        if(success) logger.info(Markers.ALTERING_SUBJECT_TABLE_MARKER,"Subject {} with {} groups has been successfully added!", name, quantOfGroups);
        else {
            notification = "Предмет не було додано!";
            logger.error(Markers.ALTERING_SUBJECT_TABLE_MARKER,"Subject {} with {} groups has not been added!", name, quantOfGroups);
        }
        redir.addFlashAttribute("success", success);
        redir.addFlashAttribute("notification", notification);
        return redirectView;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete")
    public String deleteSubject(@RequestParam Long id, Model model) throws Exception{
        subjectService.deleteSubject(id);
        logger.info(Markers.DELETE_SUBJECT_MARKER,"Subject has been successfully deleted!");
        //put info about success/failure into the model
        return "redirect:"+REDIRECT_EDIT_PAGE_URL;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update")//@RequestParam Long id,@RequestParam String newName,@RequestParam int newQuantOfGroups,@RequestParam List<Teacher> newTeachers, @RequestParam List<Specialty> newSpecialties
    public String updateSubject(Model model){
//        SubjectService.updateSubject(newName,newQuantOfSubjects, newTeachers, newSpecialties);
        //put info about success/failure into the model
        return "redirect:"+REDIRECT_EDIT_PAGE_URL;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('REGULAR')")
    @GetMapping("/get")
    public String getSubjects(){
        System.out.println(subjectService.countTeachers(4L));
        return "main";
    }
}
