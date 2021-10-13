package com.sbproject.schedule.services.implementations;

import com.sbproject.schedule.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sbproject.schedule.models.User;
import com.sbproject.schedule.services.interfaces.LoginService;

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
	public String addUser(String login, String password, String roleCode) {
		if(!roleCode.equals(adminCode) && !roleCode.equals(userCode)) // || userRepo.findByLogin(login) != null)
			return "Wrong role code!";
		if(userRepo.findByLogin(login) != null)
			return "Such account already exists!";
		userRepo.save(new User(login, password, roleCode.equals(adminCode)));
		return "SUCCESS";
	}

	/*@Override
	public void deleteUser(Long id) {
		userRepo.deleteById(id);
	}*/

	@Override
	public Iterable<User> getAll() {
		return userRepo.findAll();
	}

	@Override
	public boolean validateUser(String login, String password) {
		User user = userRepo.findByLogin(login).iterator().next();
		if(user == null || !user.getPassword().equals(password))
			return false;
		return true;
	}

}
