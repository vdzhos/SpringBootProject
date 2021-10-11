package com.sbproject.schedule.services.interfaces;

import com.sbproject.schedule.models.User;

public interface LoginService {

	String addUser(String login, String password, String roleCode);
	// void deleteUser(String login);
	 boolean validateUser(String login, String password);
	 
	 Iterable<User> getAll();

}
