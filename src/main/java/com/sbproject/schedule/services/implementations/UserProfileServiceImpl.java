package com.sbproject.schedule.services.implementations;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbproject.schedule.exceptions.user.InvalidPasswordException;
import com.sbproject.schedule.exceptions.user.UserNotFoundException;
import com.sbproject.schedule.models.User;
import com.sbproject.schedule.repositories.UserRepository;
import com.sbproject.schedule.services.interfaces.UserProfileService;
import com.sbproject.schedule.utils.Markers;

@Service
public class UserProfileServiceImpl implements UserProfileService {

	private UserRepository userRepo;
	
	private static Logger logger = LogManager.getLogger(UserProfileServiceImpl.class);
	
	@Autowired
	public void setUserRepository(UserRepository repo) {
		this.userRepo = repo;
	}
	
	@Override
	public void updatePassword(String login, String oldpassword, String newpassword) throws UserNotFoundException, InvalidPasswordException {
		Optional<User> opt = userRepo.findById(login);
		if(opt.isEmpty()) {
			logger.error(Markers.USER_MARKER, "User doesn`t exist: " + login);
			throw new UserNotFoundException("User doesn`t exist");
		}
		if(newpassword == null || newpassword.equals("")) {
			logger.error(Markers.USER_MARKER, "Invalid new password: " + newpassword);
			throw new InvalidPasswordException("Invalid password!");
		}
		User user = opt.get();
		if(!user.getPassword().equals(oldpassword)) {
			logger.error(Markers.USER_MARKER, "Wrong previous password: " + oldpassword);
			throw new InvalidPasswordException("Wrong previous password");
		}
		if(!user.getPassword().equals(newpassword)) {
			user.setPassword(newpassword);
			logger.info(Markers.USER_MARKER, "User password changed: " + login + " : New password = " + newpassword);
			this.userRepo.save(user);
		}
	}

	@Override
	public User getUserByLogin(String login) {
		Optional<User> opt = userRepo.findById(login);
		return opt.isEmpty() ? null : opt.get();
	}

}
