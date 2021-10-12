package com.sbproject.schedule.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sbproject.schedule.exceptions.user.LoginUsedException;
import com.sbproject.schedule.exceptions.user.WrongRoleCodeException;
import com.sbproject.schedule.models.User;
import com.sbproject.schedule.repositories.UserRepository;
import com.sbproject.schedule.services.interfaces.LoginService;
import com.sbproject.schedule.utils.Role;

@Service
public class LoginServiceImpl implements LoginService {

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
		if(!roleCode.equals(adminCode) && !roleCode.equals(userCode)) // || userRepo.findByLogin(login) != null)
			throw new WrongRoleCodeException("Wrong role code!");
		if(userRepo.findByLogin(login) != null)
			throw new LoginUsedException("This login is already in use!");
		userRepo.save(new User(login, password, roleCode.equals(adminCode) ? Role.ADMIN : Role.REGULAR));
	}

	/*@Override
	public void deleteUser(Long id) {
		userRepo.deleteById(id);
	}*/

	/*@Override
	public Iterable<User> getAll() {
		return userRepo.findAll();
	}*/

	@Override
	public boolean validateUser(String login, String password) {
		User user = userRepo.findByLogin(login);
		if(user == null || !user.getPassword().equals(password))
			return false;
		return true;
	}

}
