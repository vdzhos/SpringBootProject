package com.sbproject.schedule.services.interfaces;

import com.sbproject.schedule.exceptions.user.LoginUsedException;
import com.sbproject.schedule.exceptions.user.UserNotFoundException;
import com.sbproject.schedule.exceptions.user.WrongRoleCodeException;
import com.sbproject.schedule.models.User;

public interface LoginService {

	void addUser(String login, String password, String roleCode) throws LoginUsedException, WrongRoleCodeException;
	// void deleteUser(String login);
	boolean validateUser(String login, String password);
	
	User getUser(String login) throws UserNotFoundException;
	
	boolean deleteUser(String login, String password) throws UserNotFoundException;
	// Iterable<User> getAll();

}
