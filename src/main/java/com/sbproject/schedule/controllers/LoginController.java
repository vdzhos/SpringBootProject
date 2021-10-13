package com.sbproject.schedule.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sbproject.schedule.services.interfaces.LoginService;


@Controller
@RequestMapping("/login")
public class LoginController {

	private LoginService loginService;
	private String errorMessage;
	
	@Autowired
	public LoginController(LoginService loginService) {
		this.loginService = loginService;
		this.errorMessage = "";
	}
	
	@GetMapping
	public String showLoginForm(Model model) {
		model.addAttribute("errorMessage", this.errorMessage);
		return "login";
	}

	@PostMapping("/new")
	public String newUser(@RequestParam String login, @RequestParam String password, @RequestParam String role, Model model, HttpSession session){
		boolean isSuccessfull = true;
		try {
			loginService.addUser(login, password, role);
			session.setAttribute("logged", login);
		} catch (Exception e) {
			this.errorMessage = e.getMessage();
			isSuccessfull = false;
		}
		return isSuccessfull? "redirect:/" : "redirect:/login";
	}
	
	@PostMapping("/validate")
	public String validateUser(@RequestParam String login, @RequestParam String password, Model model, HttpSession session){
		boolean res = loginService.validateUser(login, password);
		System.out.println("Validated, Result = " + res);
		if(!res)
			this.errorMessage = "Failed to login";
		else {
			this.errorMessage = "";
			session.setAttribute("logged", login);
		}
        return res? "redirect:/" : "redirect:/login";
    }
	
	
}
