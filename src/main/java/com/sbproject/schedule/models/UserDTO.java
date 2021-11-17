package com.sbproject.schedule.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.sbproject.schedule.annotations.PasswordMatches;
import com.sbproject.schedule.annotations.RoleCodeMatches;


@PasswordMatches
public class UserDTO {

	@NotBlank(message = "Login must not be blank")
	@Pattern(regexp = "^(?=.{4,15}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$", message = "Login doesn't match the pattern!")
	private String login;
	@NotBlank(message = "Password must not be blank")
	@Pattern(regexp = "^(?=.{4,10}$)[a-zA-Z0-9._]+$", message = "Password doesn't match the pattern!")
	private String password;

	private String matchingPassword;
	
	@NotBlank(message = "Role code must not be blank")
	@RoleCodeMatches
	private String roleCode;
	
	
	
//	@Pattern(regexp = "^(?=.{4,10}$)[a-zA-Z0-9._]+$", message = "New password doesn't match the pattern!")
//	private String newPassword;

	public UserDTO() {
		this.login = "";
		this.password = "";
		this.matchingPassword = "";
		this.roleCode = "";
	}
	
	
	public UserDTO(String login, String password, String roleCode, String matchingPassword)
	{
		this.login = login;
		this.password = password;
		this.roleCode = roleCode;
		this.matchingPassword = matchingPassword;
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	
	public String getMatchingPassword() {
		return this.matchingPassword;
	}
	
	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}
	
}
