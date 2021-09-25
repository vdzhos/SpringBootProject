package com.sbproject.schedule.controllers;

import com.sbproject.schedule.services.implementations.SpecialtyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String addSpecialty(@RequestParam String name, @RequestParam int year, Model model){
        specialtyService.addSpecialty(name, year);
        //put info about success/failure into the model
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String deleteSpecialty(@RequestParam Long id, Model model){
        specialtyService.deleteSpecialty(id);
        //put info about success/failure into the model
        return "redirect:/";
    }

    @PostMapping("/update")//@RequestParam Long id,@RequestParam String newName,@RequestParam int newYear,
    public String updateSpecialty(Model model){
//        specialtyService.updateSpecialty(newName,newYear);
        //put info about success/failure into the model
        return "redirect:/";
    }
}
