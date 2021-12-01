package com.sbproject.schedule.services.interfaces;

import com.sbproject.schedule.exceptions.user.LoginUsedException;
import com.sbproject.schedule.exceptions.user.UserNotFoundException;
import com.sbproject.schedule.models.User;
import com.sbproject.schedule.models.UserDTO;

public interface UserService {

	User addUser(UserDTO dto) throws LoginUsedException;
	
	User getUser(String login) throws UserNotFoundException;
	
	boolean deleteUser(String login) throws UserNotFoundException;
	
	User updateUser(User user);

}
