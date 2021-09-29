package com.sbproject.schedule.controllers;

import com.sbproject.schedule.services.implementations.TeacherServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    private TeacherServiceImpl teacherServiceImpl;

    @PostMapping("/add")
    public String addTeacher(@RequestParam String name, Model model){
        teacherServiceImpl.addTeacher(name);
        //put info about success/failure into the model
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String deleteTeacher(@RequestParam Long id, Model model){
        teacherServiceImpl.deleteTeacher(id);
        //put info about success/failure into the model
        return "redirect:/";
    }

    @PostMapping("/update")
    public String updateTeacher(@RequestParam Long id, @RequestParam String newName, Model model){
        teacherServiceImpl.updateTeacher(id, newName);
        //put info about success/failure into the model
        return "redirect:/";
    }
}
