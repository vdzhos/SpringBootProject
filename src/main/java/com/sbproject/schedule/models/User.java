package com.sbproject.schedule.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sbproject.schedule.utils.Role;

@Entity
@Table(name = "USER")
public class User {

	@Id
	@Column(name = "Login", nullable = false)
	private String login;
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
	
}
