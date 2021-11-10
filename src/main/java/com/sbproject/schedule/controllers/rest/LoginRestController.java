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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;
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
	
	
	@Operation(summary = "Get user by login")
	@ApiResponses(value = { 
			  @ApiResponse(responseCode = "200", description = "Found the user", 
			    content = { @Content(mediaType = "application/json", 
			      schema = @Schema(implementation = User.class)) }),
			  @ApiResponse(responseCode = "400", description = "Invalid login supplied", 
			    content = @Content), 
			  @ApiResponse(responseCode = "404", description = "User not found", 
			    content = @Content) })
	@GetMapping("getuser/{login}")
	public ResponseEntity<User> getUser(@PathVariable @NotBlank String login) throws UserNotFoundException
	{
		User user = loginService.getUser(login);
		return ResponseEntity.ok(user);
	}
	
	
	@Operation(summary = "Registrate new user")
	@ApiResponses(value = { 
			  @ApiResponse(responseCode = "201", description = "User successfully registered", 
			    content = @Content),
			  @ApiResponse(responseCode = "400", description = "Invalid user data", 
			    content = @Content), 
			  @ApiResponse(responseCode = "403", description = "Violation of registration rules", 
			    content = @Content) })
	@PostMapping("newuser")
	public ResponseEntity<String> registrateUser(@Valid @RequestBody UserData userData) throws WrongRoleCodeException, LoginUsedException
	{
		loginService.addUser(userData.getLogin(), userData.getPassword(), userData.getRoleCode());
		return new ResponseEntity<>("Success: New user registered", HttpStatus.CREATED);
	}
	
	@Operation(summary = "Delete existing user")
	@ApiResponses(value = { 
			  @ApiResponse(responseCode = "200", description = "User successfully deleted", 
			    content = @Content),
			  @ApiResponse(responseCode = "404", description = "User not found", 
			    content = @Content), 
			  @ApiResponse(responseCode = "403", description = "Incorrect user password", 
			    content = @Content),
			  @ApiResponse(responseCode = "400", description = "Invalid user data",
			  	content = @Content)})
	@DeleteMapping("deleteuser")
	public ResponseEntity<String> deleteUser(@Valid @RequestBody UserData userData) throws UserNotFoundException
	{
		boolean succ = loginService.deleteUser(userData.getLogin(), userData.getPassword());
		return succ ? new ResponseEntity<>("Success: User deleted", HttpStatus.OK) : new ResponseEntity<>("Failure: Incorrect password", HttpStatus.FORBIDDEN);
	}
	
	
	@Operation(summary = "Change user password")
	@ApiResponses(value = { 
			  @ApiResponse(responseCode = "200", description = "User password successfully changed", 
			    content = @Content),
			  @ApiResponse(responseCode = "404", description = "User not found", 
			    content = @Content), 
			  @ApiResponse(responseCode = "403", description = "Incorrect user password", 
			    content = @Content),
			  @ApiResponse(responseCode = "400", description = "Invalid user data",
			  	content = @Content)})
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
