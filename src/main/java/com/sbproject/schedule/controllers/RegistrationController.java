package com.sbproject.schedule.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sbproject.schedule.configuration.SecurityConfig;
import com.sbproject.schedule.exceptions.user.LoginUsedException;
import com.sbproject.schedule.models.User;
import com.sbproject.schedule.models.UserDTO;
import com.sbproject.schedule.services.interfaces.UserService;

@Import(SecurityConfig.class)
@Controller
@RequestMapping("/registration")
public class RegistrationController {

	@Autowired
	private UserService service;
	@Autowired
	private AuthenticationManager authManager;
	
	@GetMapping
	public String showRegistrationForm(HttpServletRequest request, Model model) {
		UserDTO userDto = new UserDTO();
		model.addAttribute("user", userDto);
		return "registration";
	}
	
	@PostMapping
	public ModelAndView registerAccount(@ModelAttribute("user") @Valid UserDTO userDto, 
			BindingResult result,
			HttpServletRequest request,
			Errors errors) 
	{
		if(result.hasErrors())
			return new ModelAndView("registration", "user", userDto);
		try {
			User user = this.service.addUser(userDto);
			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword());

		    Authentication authentication = authManager.authenticate(authRequest);
		    SecurityContext securityContext = SecurityContextHolder.getContext();
		    securityContext.setAuthentication(authentication);
		    HttpSession session = request.getSession(true);
		    session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
		} catch(LoginUsedException expt) {
			 ModelAndView mav = new ModelAndView("registration", "user", userDto);
	         mav.addObject("message", expt.getMessage());
	         return mav;
		}
		
		return new ModelAndView("redirect:/", "user", userDto);
	}
	
}
