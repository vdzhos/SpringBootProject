package com.sbproject.schedule.repositories;//package com.sbproject.schedule.repositories_fakes.interfaces;

import com.sbproject.schedule.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {


	//User findById(Long id);
	Iterable<User> findByLogin(String login);

}
