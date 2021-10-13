package com.sbproject.schedule.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbproject.schedule.exceptions.user.InvalidPasswordException;
import com.sbproject.schedule.exceptions.user.UserNotFoundException;
import com.sbproject.schedule.models.User;
import com.sbproject.schedule.repositories.UserRepository;
import com.sbproject.schedule.services.interfaces.UserProfileService;

@Service
public class UserProfileServiceImpl implements UserProfileService {

	private UserRepository userRepo;
	
	@Autowired
	public void setUserRepository(UserRepository repo) {
		this.userRepo = repo;
	}
	
	@Override
	public void updatePassword(String login, String newpassword) throws UserNotFoundException, InvalidPasswordException {
		User user = userRepo.findByLogin(login);
		if(user == null)
			throw new UserNotFoundException("User doesn`t exist");
		if(newpassword == null || newpassword.equals(""))
			throw new InvalidPasswordException("Invalid password!");
		if(!user.getPassword().equals(newpassword)) {
			user.setPassword(newpassword);
			this.userRepo.save(user);
		}
	}

	@Override
	public User getUserByLogin(String login) {
		return userRepo.findByLogin(login);
	}

}
