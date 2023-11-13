package com.backend.tomato;

import com.backend.tomato.dao.UserDao;
import com.backend.tomato.entitites.User;
import com.backend.tomato.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TomatoApplication{

	public static void main(String[] args) {
		SpringApplication.run(TomatoApplication.class, args);
	}


//	@Autowired
//	UserService userService;
//
//	@Override
//	public void run(String... args) throws Exception {
//		User user1 = new User();
//		user1.setEmail("ayush@tomato.com");
//		user1.setPassword("ayush");
//		user1.setFirstName("Ayush");
//		user1.setLastName("Sharma");
//		user1.setRole("ADMIN");
//		user1.setAbout("Competitive Programmer");
//
//		User user2 = new User();
//		user2.setPassword("harsh");
//		user2.setFirstName("Harshnite");
//		user2.setLastName("Prasad");
//		user2.setEmail("harsh@tomato.com");
//		user2.setRole("USER");
//		user2.setAbout("SDE @Samsung");
//
//		User user3 = new User();
//		user3.setPassword("aditya");
//		user3.setFirstName("Aditya");
//		user3.setLastName("Mishra");
//		user3.setEmail("aditya@tomato.com");
//		user3.setRole("USER");
//		user3.setAbout("HS Only");
//
//		this.userService.createUser(user1);
//		this.userService.createUser(user2);
//		this.userService.createUser(user3);
//
//	}
}
