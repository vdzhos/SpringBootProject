package com.sbproject.schedule.controllers;

import com.sbproject.schedule.services.implementations.SpecialtyServiceImpl;
import com.sbproject.schedule.services.implementations.SubjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * loads all the data in all the tabs on the settings page
 */
@Controller
public class MainController {


    @Autowired
    private SpecialtyServiceImpl specialtyService;
    @Autowired
    private SubjectServiceImpl subjectService;


    @GetMapping
    public String getAll(Model model){
        model.addAttribute("specialties",specialtyService.getAll());
        model.addAttribute("subjects",subjectService.getAll());
        return "main";
    }




}
