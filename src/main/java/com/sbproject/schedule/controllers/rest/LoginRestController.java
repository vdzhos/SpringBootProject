package com.sbproject.schedule.controllers.rest;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sbproject.schedule.exceptions.user.InvalidPasswordException;
import com.sbproject.schedule.exceptions.user.LoginUsedException;
import com.sbproject.schedule.exceptions.user.UserNotFoundException;
import com.sbproject.schedule.models.User;
import com.sbproject.schedule.models.UserDTO;
import com.sbproject.schedule.services.interfaces.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;
@RestController
@RequestMapping("/restLogin")
public class LoginRestController {

	private UserService loginService;
	private AuthenticationManager authManager;
	
	@Autowired
	public LoginRestController(UserService loginService, AuthenticationManager authManager)
	{
		this.loginService = loginService;
		this.authManager = authManager;
	}

	
	@Operation(summary = "Authenticate user")
	@ApiResponses(value = { 
			  @ApiResponse(responseCode = "200", description = "User successfully authenticated", 
			    content = @Content),
			  @ApiResponse(responseCode = "404", description = "User not found", 
			    content = @Content), 
			  @ApiResponse(responseCode = "403", description = "Incorrect user password", 
			    content = @Content),
			  @ApiResponse(responseCode = "400", description = "Invalid user data",
			  	content = @Content)})
	@PostMapping("authenticate")
	public ResponseEntity<String> authenticate(@NotBlank @RequestParam String login, @NotBlank @RequestParam String password) throws UserNotFoundException, InvalidPasswordException
	{
		User user = loginService.getUser(login);
		if(!user.getPassword().equals(password))
			throw new InvalidPasswordException("Invalid password");
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword());

	    Authentication authentication = authManager.authenticate(authRequest);
	    SecurityContext securityContext = SecurityContextHolder.getContext();
	    securityContext.setAuthentication(authentication);
	    return new ResponseEntity<>("You have successfully logged in", HttpStatus.OK);
	}
	
	
	@Operation(summary = "Get currently logged user")
	@ApiResponses(value = { 
			  @ApiResponse(responseCode = "200", description = "Found the user", 
			    content = { @Content(mediaType = "application/json", 
			      schema = @Schema(implementation = User.class)) }),
			  @ApiResponse(responseCode = "404", description = "User not found", 
			    content = @Content) })
	@GetMapping
	public ResponseEntity<User> getLoggedUser() throws UserNotFoundException
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userLogin = authentication.getName();
		User user = loginService.getUser(userLogin);
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
	@PostMapping("/newuser")
	public ResponseEntity<String> registrateUser(@Valid @RequestBody UserDTO userData) throws LoginUsedException
	{
		loginService.addUser(userData);
		return new ResponseEntity<>("Success: New user registered", HttpStatus.CREATED);
	}

	
	@Operation(summary = "Delete currently logged user")
	@ApiResponses(value = { 
			  @ApiResponse(responseCode = "200", description = "User successfully deleted", 
			    content = @Content),
			  @ApiResponse(responseCode = "404", description = "User not found", 
			    content = @Content)})
	@DeleteMapping
	public ResponseEntity<String> deleteLoggedUser() throws UserNotFoundException 
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userLogin = authentication.getName();
		loginService.deleteUser(userLogin);
		SecurityContextHolder.clearContext();
		return new ResponseEntity<>("Your account has been deleted", HttpStatus.OK);
	}
	
	@Operation(summary = "Change password of currently logged user")
	@ApiResponses(value = { 
			  @ApiResponse(responseCode = "200", description = "User password successfully changed", 
			    content = @Content),
			  @ApiResponse(responseCode = "404", description = "User not found", 
			    content = @Content), 
			  @ApiResponse(responseCode = "400", description = "Invalid new password",
			  	content = @Content)})
	@PutMapping("passupdate")
	public ResponseEntity<String> updatePassword(@RequestParam
			         							 @NotBlank
			         							 @Size(min = 4)
												 @Size(max = 20)
												 @Pattern(regexp = "^[a-zA-Z0-9._]+$") String newPassword) throws UserNotFoundException
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userLogin = authentication.getName();
		User user = this.loginService.getUser(userLogin);
		user.setPassword(newPassword);
		this.loginService.updateUser(user);
		return new ResponseEntity<>("Your password has been changed", HttpStatus.OK);
	}
	
///exception handlers
	
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<String> handle(UserNotFoundException e)
	{
		return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler({LoginUsedException.class, InvalidPasswordException.class})
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
