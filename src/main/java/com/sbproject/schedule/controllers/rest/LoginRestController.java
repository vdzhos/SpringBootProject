package com.sbproject.schedule.controllers.rest;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sbproject.schedule.exceptions.user.InvalidPasswordException;
import com.sbproject.schedule.exceptions.user.LoginUsedException;
import com.sbproject.schedule.exceptions.user.UserNotFoundException;
import com.sbproject.schedule.exceptions.user.WrongRoleCodeException;
import com.sbproject.schedule.models.User;
import com.sbproject.schedule.models.UserData;
import com.sbproject.schedule.services.interfaces.LoginService;
import com.sbproject.schedule.services.interfaces.UserProfileService;

@RestController
@RequestMapping("/restLogin")
public class LoginRestController {

	private LoginService loginService;
	private UserProfileService userService;
	
	@Autowired
	public LoginRestController(LoginService loginService, UserProfileService userService)
	{
		this.loginService = loginService;
		this.userService = userService;
	}
	
	@GetMapping("getuser/{login}")
	public ResponseEntity<User> getUser(@PathVariable @NotBlank String login) throws UserNotFoundException
	{
		User user = loginService.getUser(login);
		return ResponseEntity.ok(user);
	}
	
	@PostMapping("newuser")
	public ResponseEntity<String> registrateUser(@Valid @RequestBody UserData userData) throws WrongRoleCodeException, LoginUsedException
	{
		loginService.addUser(userData.getLogin(), userData.getPassword(), userData.getRoleCode());
		return new ResponseEntity<>("Success: New user registered", HttpStatus.OK);
	}
	
	@DeleteMapping("deleteuser")
	public ResponseEntity<String> deleteUser(@Valid @RequestBody UserData userData) throws UserNotFoundException
	{
		boolean succ = loginService.deleteUser(userData.getLogin(), userData.getPassword());
		return succ ? new ResponseEntity<>("Success: User deleted", HttpStatus.OK) : new ResponseEntity<>("Incorrect password: ", HttpStatus.FORBIDDEN);
	}
	
	@PutMapping("passupdate")
	public ResponseEntity<String> updatePassword(@Valid @RequestBody UserData userData) throws UserNotFoundException, InvalidPasswordException {
		userService.updatePassword(userData.getLogin(), userData.getPassword(), userData.getNewPassword());
		return new ResponseEntity<>("Success: Password changed", HttpStatus.OK);
	}
	
	
///exception handlers
	
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<String> handle(UserNotFoundException e)
	{
		return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler({WrongRoleCodeException.class, LoginUsedException.class, InvalidPasswordException.class})
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ResponseEntity<String> handleMultiple(Exception e)
	{
		return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.FORBIDDEN);
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<String> handle(MethodArgumentNotValidException e) {
		return new ResponseEntity<>("Object not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	  @ResponseStatus(HttpStatus.BAD_REQUEST)
	  public ResponseEntity<String> handle(ConstraintViolationException e) {
	    return new ResponseEntity<>("Parameter not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
	  }
	
}
