package com.sbproject.schedule.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sbproject.schedule.exceptions.user.UserNotFoundException;
import com.sbproject.schedule.models.User;
import com.sbproject.schedule.services.interfaces.UserService;
import com.sbproject.schedule.utils.Role;

@Controller
@RequestMapping("/profile")
public class UserProfileController {

	private UserService userService;
	private String loggedUsername;
	private Role loggedRole;

	
	@Autowired
	public UserProfileController(UserService userService) {
		this.userService = userService;
		loggedUsername = "";
		loggedRole = Role.REGULAR;
	}
	
	
	@GetMapping
	public String showProfile(Model model, Authentication auth) throws UserNotFoundException {
		User loggedUser = new User(userService.getUser(auth.getName()));
		model.addAttribute("user", loggedUser);
		this.loggedRole = loggedUser.getRole();
		this.loggedUsername = loggedUser.getLogin();
		return "profile";
	}
	
	
	@PostMapping 
	public ModelAndView updatePassword(@ModelAttribute("user") @Valid User user,
			BindingResult result,
			Errors errors)
	{
		user.setRole(loggedRole);
		user.setLogin(loggedUsername);
		if(result.hasErrors())
			return new ModelAndView("profile", "user", user);
		this.userService.updateUser(user);
		ModelAndView mav = new ModelAndView("profile", "user", user);
        mav.addObject("message", "Password updated");
        return mav;
	}
	
	@RequestMapping("/back")
	public String returnToMainPage() {
		return "redirect:/";
	}	
	
}
