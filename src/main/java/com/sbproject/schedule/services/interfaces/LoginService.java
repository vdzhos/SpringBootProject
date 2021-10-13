package com.sbproject.schedule.services.interfaces;

import com.sbproject.schedule.exceptions.user.LoginUsedException;
import com.sbproject.schedule.exceptions.user.WrongRoleCodeException;

public interface LoginService {

	void addUser(String login, String password, String roleCode) throws LoginUsedException, WrongRoleCodeException;
	// void deleteUser(String login);
	boolean validateUser(String login, String password);
	 
	// Iterable<User> getAll();

}
