package com.sbproject.schedule.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbproject.schedule.models.User;
import com.sbproject.schedule.repositories.fakes.interfaces.UserRepository;
import com.sbproject.schedule.services.interfaces.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	private UserRepository userRepo;

	@Autowired
	public void setUserRepository(UserRepository repo) {
		this.userRepo = repo;
	}
	
	@Override
	public boolean addUser(String login, String password, boolean isAdmin) {
		if(userRepo.findByLogin(login) != null)
			return false;
		userRepo.save(new User(login, password, isAdmin));
		return true;
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
		User user = userRepo.findByLogin(login);
		if(user == null || !user.getPassword().equals(password))
			return false;
		return true;
	}

}
