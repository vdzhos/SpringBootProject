package com.sbproject.schedule.controllers;

import com.sbproject.schedule.services.interfaces.SpecialtyService;
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

/**
 * controls manipulations with specialties
 */
@Controller
@RequestMapping("/specialty")
public class SpecialtyController {


    private SpecialtyService specialtyService;

    @Autowired
    public SpecialtyController(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public RedirectView addSpecialty(@RequestParam String name, @RequestParam int year, Model model, RedirectAttributes redir){
        RedirectView redirectView= new RedirectView("/",true);
        String notification = "Спеціальність '"+name+" - "+year+"' було успішно додано!";
        boolean success = true;
        try{
            specialtyService.addSpecialty(name, year);
        } catch (Exception e) {
            success = false;
            notification = e.getMessage();
        }
        redir.addFlashAttribute("showNotification", true);
        redir.addFlashAttribute("success", success);
        redir.addFlashAttribute("notification",notification);
        return redirectView;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/delete")
    public RedirectView deleteSpecialty(@RequestParam Long id, Model model, @RequestParam String specialtyToString, RedirectAttributes redir){
        ThreadContext.put("specialty",specialtyToString);
        specialtyService.deleteSpecialty(id);
        ThreadContext.clearAll();
        RedirectView redirectView= new RedirectView("/",true);
        String notification = "Спеціальність було успішно видалено!";
        redir.addFlashAttribute("showNotification", true);
        redir.addFlashAttribute("success", true);
        redir.addFlashAttribute("notification",notification);
        return redirectView;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/update")
    public RedirectView updateSpecialty(@RequestParam Long id,@RequestParam String specName,@RequestParam int specYear, RedirectAttributes redir){
        RedirectView redirectView= new RedirectView("/",true);
        String notification = "Спеціальність було успішно оновлено на '"+specName+" - "+specYear+"'!";
        boolean success = true;
        try{
            specialtyService.updateSpecialty(id,specName,specYear);
        } catch (Exception e) {
            success = false;
            notification = e.getMessage();
        }
        redir.addFlashAttribute("showNotification", true);
        redir.addFlashAttribute("success", success);
        redir.addFlashAttribute("notification",notification);
        return redirectView;
    }

}


