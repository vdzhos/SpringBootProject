package com.sbproject.schedule.models;

public class User {

	//private Long id; actually, user login serves as id
	private String login;
	private String password;
	private boolean isAdmin;
	
	public User() {}
	
	public User(String login, String password, boolean isAdmin) {
		this.login = login;
		this.password = password;
		this.isAdmin = isAdmin;
	}
	
	public String getLogin() {
		return this.login;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public boolean isAdmin() {
		return this.isAdmin;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public void setPassword(String pswrd) {
		this.password = pswrd;
	}
	
	public void setRole(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	@Override
	public String toString() {
		return "User{" +
                "login = " + this.login + "\\ " +
                "password = " + this.password + "\\ " +
                "role = " + (isAdmin? "admin" : "regular user")+ '}';
	}
	
}
