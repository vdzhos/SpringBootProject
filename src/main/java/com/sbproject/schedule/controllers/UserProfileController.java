package com.sbproject.schedule.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sbproject.schedule.models.User;
import com.sbproject.schedule.services.interfaces.UserProfileService;

@Controller
@RequestMapping("/profile")
public class UserProfileController {

	private UserProfileService userService;
	private String errorMessage;
	private User currentUser;
	
	@Autowired
	public UserProfileController(UserProfileService userService) {
		this.errorMessage = "";
		this.userService = userService;
	}
	
	@GetMapping
	public String showProfile(Model model, HttpSession session) {
		errorMessage = "";
		User loggedUser = userService.getUserByLogin((String)session.getAttribute("logged"));
		this.currentUser = loggedUser;
		System.out.println(loggedUser);
		model.addAttribute("ulogin", loggedUser.getLogin());
		model.addAttribute("upassword", loggedUser.getPassword());
		model.addAttribute("urole", loggedUser.getRole().name());
		model.addAttribute("errorMessage", this.errorMessage);
		return "profile";
	}
	
	
	@PostMapping("/passUpdate")
	public String updatePassword(@RequestParam String password) {
		try {
			userService.updatePassword(currentUser.getLogin(), password);
			errorMessage = "";
		} catch(Exception e) {
			errorMessage = e.getMessage();
		}
		return "redirect:/profile";
	}
	
	@RequestMapping("/back")
	public String returnToMainPage() {
		return "redirect:/";
	}
	
}
