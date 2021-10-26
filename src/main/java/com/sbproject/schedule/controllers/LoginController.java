package com.sbproject.schedule.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.sbproject.schedule.exceptions.user.LoginUsedException;
import com.sbproject.schedule.exceptions.user.UserNotFoundException;
import com.sbproject.schedule.exceptions.user.WrongRoleCodeException;
import com.sbproject.schedule.models.User;
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
		if(!res)
			this.errorMessage = "Failed to login";
		else {
			this.errorMessage = "";
			session.setAttribute("logged", login);
		}
        return res? "redirect:/" : "redirect:/login";
    }
	

///////////////rest methods ///////////////
	@GetMapping("rest/getuser")
	@ResponseBody
	public User restGetUser(@RequestParam String login) {
		User user;
		try {
			user = loginService.getUser(login);
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
		return user;
	}
	
	@PostMapping("rest/newuser")
	@ResponseBody
	public String restNewUser(@RequestParam String login, @RequestParam String password, @RequestParam String role) {
		System.out.println("IN NEW USER");
		try {
			loginService.addUser(login, password, role);
			//session.setAttribute("logged", login);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage(), e);
		}
		return HttpStatus.CREATED.toString();//"Success";
	}
	
	@DeleteMapping("rest/deleteuser")
	@ResponseBody
	public String restDeleteUser(@RequestParam String login, @RequestParam String password) {
		boolean isSuccessfull = false;
		try {
			isSuccessfull = this.loginService.deleteUser(login, password);
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage(), e);
		}
		return isSuccessfull ? HttpStatus.OK.toString() : HttpStatus.FORBIDDEN.toString() + " Wrong password";
	}
	
}
