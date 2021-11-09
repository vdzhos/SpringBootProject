package com.sbproject.schedule.services.implementations;

import com.sbproject.schedule.repositories.UserRepository;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sbproject.schedule.exceptions.user.LoginUsedException;
import com.sbproject.schedule.exceptions.user.UserNotFoundException;
import com.sbproject.schedule.exceptions.user.WrongRoleCodeException;
import com.sbproject.schedule.models.User;
import com.sbproject.schedule.services.interfaces.LoginService;
import com.sbproject.schedule.utils.Markers;
import com.sbproject.schedule.utils.Role;

@Service
public class LoginServiceImpl implements LoginService {

	private static Logger logger = LogManager.getLogger(LoginServiceImpl.class);
	
	private UserRepository userRepo;

	@Value("${custom.admin-code}")
	private String adminCode;

	@Value("${custom.user-code}")
	private String userCode;

	@Autowired
	public void setUserRepository(UserRepository repo) {
		this.userRepo = repo;
	}

	@Override
	public void addUser(String login, String password, String roleCode) throws LoginUsedException, WrongRoleCodeException {
		if(!roleCode.equals(adminCode) && !roleCode.equals(userCode)) { // || userRepo.findByLogin(login) != null)
			logger.error(Markers.USER_MARKER, "Wrong role code: " + roleCode);
			throw new WrongRoleCodeException("Wrong role code!");
		}
		if(!userRepo.findById(login).isEmpty()) {
			logger.error(Markers.USER_MARKER, "Login is already in use: " + login);
			throw new LoginUsedException("This login is already in use!");
		}
		Role role = roleCode.equals(adminCode) ? Role.ADMIN : Role.REGULAR;
		logger.info(Markers.USER_MARKER, "User registrated: " + login + " : " + password + " : " + role.name());
		userRepo.save(new User(login, password, role));
	}


	/*@Override
	public Iterable<User> getAll() {
		return userRepo.findAll();
	}*/

	@Override
	public boolean validateUser(String login, String password) {
		Optional<User> opt = userRepo.findById(login);
		if(opt.isEmpty())
			return false;
		if(!opt.get().getPassword().equals(password))
			return false;
		logger.info(Markers.USER_MARKER, "User logged in: " + login + " : " + password);
		return true;
	}
	

	@Override
	public User getUser(String login) throws UserNotFoundException {
		Optional<User> opt = userRepo.findById(login);
		if(opt.isEmpty()) {
			logger.error(Markers.USER_MARKER, "User not found: " + login);
			throw new UserNotFoundException("User not found!");
		}
		return opt.get();
	}

	@Override
	public boolean deleteUser(String login, String password) throws UserNotFoundException {
		Optional<User> opt = userRepo.findById(login);
		if(opt.isEmpty()) {
			logger.error(Markers.USER_MARKER, "User not found: " + login);
			throw new UserNotFoundException("User not found!");
		}
		if(!password.equals(opt.get().getPassword()))
			return false;
		userRepo.deleteById(login);
		logger.info(Markers.USER_MARKER, "User deleted: " + login + " : " + password);
		return true;
	}

}
