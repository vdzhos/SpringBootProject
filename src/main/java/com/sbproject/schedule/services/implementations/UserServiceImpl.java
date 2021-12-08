package com.sbproject.schedule.services.implementations;

import com.sbproject.schedule.repositories.UserRepository;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.sbproject.schedule.exceptions.user.LoginUsedException;
import com.sbproject.schedule.exceptions.user.UserNotFoundException;
import com.sbproject.schedule.models.User;
import com.sbproject.schedule.models.UserDTO;
import com.sbproject.schedule.services.interfaces.UserService;
import com.sbproject.schedule.utils.Markers;
import com.sbproject.schedule.utils.Role;

@Service
public class UserServiceImpl implements UserService {

	private static Logger logger = LogManager.getLogger(UserServiceImpl.class);
	
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
	public User addUser(UserDTO dto) throws LoginUsedException {
		if(!userRepo.findById(dto.getLogin()).isEmpty()) {
			logger.error(Markers.USER_MARKER, "Login is already in use: " + dto.getLogin());
			throw new LoginUsedException("This login is already in use!");
		}
		Role role = dto.getRoleCode().equals(adminCode) ? Role.ADMIN : Role.REGULAR;
		logger.info(Markers.USER_MARKER, "User registrated: " + dto.getLogin() + " : " + dto.getPassword() + " : " + role.name());
		User u = new User(dto.getLogin(), dto.getPassword(), role);
		userRepo.save(u);
		return u;
	}


	@Cacheable(cacheNames = "users", key="#login")
	@Override
	public User getUser(String login) throws UserNotFoundException {
		Optional<User> opt = userRepo.findById(login);
		if(opt.isEmpty()) {
			logger.error(Markers.USER_MARKER, "User not found: " + login);
			throw new UserNotFoundException("User not found!");
		}
		return opt.get();
	}

	@CacheEvict(cacheNames="users", key="#login")
	@Override
	public boolean deleteUser(String login) throws UserNotFoundException {
		Optional<User> opt = userRepo.findById(login);
		if(opt.isEmpty()) {
			logger.error(Markers.USER_MARKER, "User not found: " + login);
			throw new UserNotFoundException("User not found!");
		}
		userRepo.deleteById(login);
		logger.info(Markers.USER_MARKER, "User deleted: " + login);
		return true;
	}

	@CachePut(cacheNames = "users", key = "#user.login")
	@Override
	public User updateUser(User user) {
		userRepo.deleteById(user.getLogin());
		userRepo.save(user);
		return user;
	}

	
	@Scheduled(cron = "0 */5 * ? * *")
    @CacheEvict(cacheNames = "users", allEntries = true)
	public void clearUsersCache()
	{
		logger.info(Markers.USER_CACHING_MARKER, "SCHEDULED REMOVAL: All users removed from cache");
	}
			
}
