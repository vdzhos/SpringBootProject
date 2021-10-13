package com.sbproject.schedule.repositories;//package com.sbproject.schedule.repositories_fakes.interfaces;

import com.sbproject.schedule.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {


	//User findById(Long id);
	Iterable<User> findByLogin(String login);

}
