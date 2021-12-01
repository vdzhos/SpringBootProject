package com.sbproject.schedule.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.sbproject.schedule.utils.Role;

@Entity
@Table(name = "USER")
public class User {

	@Id
	@Column(name = "Login", nullable = false)
	private String login;
	@NotBlank(message = "Password must not be blank")
	@Size(min = 4, message = "Password must be at least 4 characters long!")
	@Size(max = 20, message = "Password must be no longer than 4 characters!")
	@Pattern(regexp = "^[a-zA-Z0-9._]+$", message = "Password must contain only digits, latin letters, dots and underscore characters!")
	@Column(name = "Password", nullable = false)
	private String password;
	@Column(name = "Role", nullable = false)
	private Role role;
	
	public User() {}
	
	public User(User that) {
		this.login = that.getLogin();
		this.password = that.getPassword();
		this.role = that.getRole();
	}
	
	public User(String login, String password, Role role) {
		this.login = login;
		this.password = password;
		this.role = role;
	}
	
	public String getLogin() {
		return this.login;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public Role getRole() {
		return this.role;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public void setPassword(String pswrd) {
		this.password = pswrd;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
	
	@Override
	public String toString() {
		return "User{" +
                "login = " + this.login + "\\ " +
                "password = " + this.password + "\\ " +
                "role = " + this.role.name() + '}';
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (role != other.role)
			return false;
		return true;
	}
	
}
