package com.sbproject.schedule.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sbproject.schedule.models.User;
import com.sbproject.schedule.utils.Role;

@Repository
public interface UserRepository extends CrudRepository<User, String>{

	//User findByLogin(String login);
    Iterable<User> findByRole(Role role);
}
