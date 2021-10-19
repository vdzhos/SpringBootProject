package com.sbproject.schedule.controllers;

import com.sbproject.schedule.services.implementations.SpecialtyServiceImpl;
import com.sbproject.schedule.utils.Markers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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


    private SpecialtyServiceImpl specialtyService;

    @Autowired
    public SpecialtyController(SpecialtyServiceImpl specialtyService) {
        this.specialtyService = specialtyService;
    }

    private static Logger logger = LoggerFactory.getLogger(SpecialtyController.class);

    @PostMapping("/add")
    public RedirectView addSpecialty(@RequestParam String name, @RequestParam int year, Model model, RedirectAttributes redir){
        RedirectView redirectView= new RedirectView("/",true);
        String notification = "Спеціальність '"+name+" - "+year+"' було успішно додано!";
        boolean success = true;
        try{
            specialtyService.addSpecialty(name, year);
            logger.info("Specialty {}-{} added",name,year);
            logger.info(Markers.marker,"Specialty {}-{} added",name,year);
        } catch (Exception e) {
            success = false;
            notification = e.getMessage();
            logger.error("Specialty {}-{} not added",name,year,e);
        }
        redir.addFlashAttribute("showNotification", true);
        redir.addFlashAttribute("success", success);
        redir.addFlashAttribute("notification",notification);
        return redirectView;
    }

    @PostMapping("/delete")
    public RedirectView deleteSpecialty(@RequestParam Long id, Model model, RedirectAttributes redir){
        specialtyService.deleteSpecialty(id);
        RedirectView redirectView= new RedirectView("/",true);
        String notification = "Спеціальність було успішно видалено!";
        redir.addFlashAttribute("showNotification", true);
        redir.addFlashAttribute("success", true);
        redir.addFlashAttribute("notification",notification);
        return redirectView;
    }


    @PostMapping("/update")//@RequestParam Long id,@RequestParam String newName,@RequestParam int newYear,
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






//    @PostMapping("/delete")
//    public String deleteSpecialty(@RequestParam Long id, Model model){
//        specialtyService.deleteSpecialty(id);
//        //put info about success/failure into the model
//        return "redirect:/";
//    }
//
//    @PostMapping("/update")//@RequestParam Long id,@RequestParam String newName,@RequestParam int newYear,
//    public String updateSpecialty(Model model){
////        specialtyService.updateSpecialty(newName,newYear);
//        //put info about success/failure into the model
//        return "redirect:/";
//    }

    @GetMapping("/get")
    public String getSpecialties(){

        return "main";
    }
}
