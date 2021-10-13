package com.sbproject.schedule.controllers;

import com.sbproject.schedule.services.implementations.SpecialtyServiceImpl;
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
        redir.addFlashAttribute("success", success);
        redir.addFlashAttribute("notification",notification);
        return redirectView;
    }

    @PostMapping("/delete")
    public RedirectView deleteSpecialty(@RequestParam Long id, Model model, RedirectAttributes redir){
        specialtyService.deleteSpecialty(id);
        RedirectView redirectView= new RedirectView("/",true);
        String notification = "Спеціальність було успішно видалено!";
        redir.addFlashAttribute("success", true);
        redir.addFlashAttribute("notification",notification);
        return redirectView;
    }


    @PostMapping("/update")//@RequestParam Long id,@RequestParam String newName,@RequestParam int newYear,
    public RedirectView updateSpecialty(Model model, RedirectAttributes redir){
        RedirectView redirectView= new RedirectView("/",true);
//        String notification = "Спеціальність було успішно оновлено на '"+newName+" - "+newYear+"'!";
//        boolean success = true;
//        try{
//            specialtyService.updateSpecialty(id,newName,newYear);
//        } catch (Exception e) {
//            success = false;
//            notification = e.getMessage();
//        }
//        redir.addFlashAttribute("success", success);
//        redir.addFlashAttribute("notification",notification);
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
