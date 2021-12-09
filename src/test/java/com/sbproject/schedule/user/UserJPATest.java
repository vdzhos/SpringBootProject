package com.sbproject.schedule.user;

import com.sbproject.schedule.models.User;
import com.sbproject.schedule.repositories.UserRepository;
import com.sbproject.schedule.utils.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserJPATest {

	private UserRepository userRepo;
	
	@Autowired
	public UserJPATest(UserRepository userRepo)
	{
		this.userRepo = userRepo;
		userRepo.deleteAll();
	}
	
	@Test
	public void givenUserObject_saveInDB_thenUserObject()
	{
		User u2 = userRepo.save(new User("gleb", "2222", Role.ADMIN));
		
		assertThat(u2).hasFieldOrPropertyWithValue("login", "gleb");
        assertThat(u2).hasFieldOrPropertyWithValue("password", "2222");
        assertThat(u2).hasFieldOrPropertyWithValue("role", Role.ADMIN);
	}
	
	@Test
	public void givenUserObjects_findAllObjects_thenIterableContains()
	{
		User u1 = new User("login1", "2222", Role.ADMIN);
		User u2 = new User("login2", "3333", Role.REGULAR);
		userRepo.save(u1);
		userRepo.save(u2);
		assertThat(userRepo.findAll()).hasSize(2).contains(u1,u2);
	}
	
	@Test
	public void givenUserObject_findById_thenUserObject()
	{
		User u1 = new User("login1", "1111", Role.ADMIN);
		userRepo.save(u1);
		assertThat(userRepo.findById("login1").get()).hasFieldOrPropertyWithValue("login", "login1");
	}
	
	@Test
	public void givenUserObjects_findByRole_thenIterableContains()
	{
		User u1 = new User("login1", "1111", Role.ADMIN);
		User u2 = new User("login2", "2222", Role.ADMIN);
		User u3 = new User("login3", "0000", Role.REGULAR);
		userRepo.save(u1);
		userRepo.save(u2);
		userRepo.save(u3);
		assertThat(userRepo.findByRole(Role.ADMIN)).hasSize(2).contains(u1,u2);
	}
	
	@Test
	public void givenUserObjects_deleteById_thenIterableNotContain()
	{
		User u1 = new User("login1", "1111", Role.ADMIN);
		User u2 = new User("login2", "2222", Role.ADMIN);
		userRepo.save(u1);
		userRepo.save(u2);

		userRepo.deleteById("login1");
		assertThat(userRepo.findAll()).hasSize(1).contains(u2);
	}
	
	
	@Test
	public void givenUserObjects_deleteAll_thenIterableIsEmpty()
	{
		User u1 = new User("login1", "1111", Role.ADMIN);
		User u2 = new User("login2", "2222", Role.ADMIN);
		userRepo.save(u1);
		userRepo.save(u2);
		userRepo.deleteAll();
		assertThat(userRepo.findAll()).isEmpty();
	}
}
