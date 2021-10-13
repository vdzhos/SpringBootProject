package com.sbproject.schedule.services.interfaces;

import com.sbproject.schedule.exceptions.user.InvalidPasswordException;
import com.sbproject.schedule.exceptions.user.UserNotFoundException;
import com.sbproject.schedule.models.User;

public interface UserProfileService {

	void updatePassword(String login, String newpassord) throws UserNotFoundException, InvalidPasswordException;
	User getUserByLogin(String login);
}
