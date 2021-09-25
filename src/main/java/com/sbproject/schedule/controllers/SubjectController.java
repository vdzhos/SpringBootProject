package com.sbproject.schedule.controllers;

import com.sbproject.schedule.models.Teacher;
import com.sbproject.schedule.services.implementations.SubjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * controls manipulations with subjects
 */
@Controller
@RequestMapping("/subject")
public class SubjectController {

    private SubjectServiceImpl subjectService;

    @Autowired
    public SubjectController(SubjectServiceImpl subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping("/add")
    public String addSubject(@RequestParam String name, @RequestParam int quantOfGroups, @RequestParam List<Teacher> teachers, Model model){
        subjectService.addSubject(name, quantOfGroups, teachers);
        //put info about success/failure into the model
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String deleteSubject(@RequestParam Long id, Model model){
        subjectService.deleteSubject(id);
        //put info about success/failure into the model
        return "redirect:/";
    }

    @PostMapping("/update")//@RequestParam Long id,@RequestParam String newName,@RequestParam int newQuantOfGroups,@RequestParam List<Teacher> newTeachers
    public String updateSubject(Model model){
//        SubjectService.updateSubject(newName,newQuantOfSubjects, newTeachers);
        //put info about success/failure into the model
        return "redirect:/";
    }
}
