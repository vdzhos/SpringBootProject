package com.sbproject.schedule.services.interfaces;

import com.sbproject.schedule.models.User;

public interface LoginService {
	
	 boolean addUser(String login, String password, boolean isAdmin);
	// void deleteUser(String login);
	 boolean validateUser(String login, String password);
	 
	 Iterable<User> getAll();

}
