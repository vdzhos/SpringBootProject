package com.sbproject.schedule.repositories.fakes.implementations;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sbproject.schedule.models.User;
import com.sbproject.schedule.repositories.fakes.interfaces.UserRepository;

@Component
public class UserRepositoryImpl implements UserRepository {

	private Map<String, User> userMap;
	
	@Autowired
	public UserRepositoryImpl(Map<String, User> map) {
		this.userMap = map;
	}
	
	@Override
	public Iterable<User> findAll() {
		return userMap.values();
	}

	/*@Override
	public User findById(Long id) {
		return userMap.get(id);
	}*/

	@Override
	public User findByLogin(String login) {
		return userMap.get(login);
	}

	@Override
	public Iterable<User> findByRole(boolean admins) {
		return userMap.values();
	}

	@Override
	public User save(User u) {
		this.userMap.put(u.getLogin(), u);
		System.out.println("Saved user: \n" + u);
		return u;
	}

	@Override
	public boolean deleteByLogin(String login) {
		boolean res = false;
		if(userMap.containsKey(login)) {
			userMap.remove(login);
			System.out.println("User with login = " + login +" has been deleted!");
			res = true;
		}
		return res;
	}
	
	/*@Override
	public void deleteById(Long id) {
		userMap.remove(id);
        System.out.println("User with id = " + id +" has been deleted!");
		
	}*/

}
