package com.backend.tomato.services;

import com.backend.tomato.dao.UserDao;
import com.backend.tomato.entitites.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private static final String ROLE="USER";

    @Autowired
    private UserDao userDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    Logger logger= LoggerFactory.getLogger(UserService.class);

    public User createUser(User user){
        user.setUserId(UUID.randomUUID().toString());
        user.setRole(ROLE);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(user.toString());
        logger.info("Creating User {} ",user);
        return this.userDao.save(user);
    }

    public User fetchUserById(String userId){
        Optional<User> userOptional = userDao.findById(userId);
        return userOptional.orElse(null);
    }

    public List<User> getUsers(){
        return this.userDao.findAll();
    }

    public boolean doesUserExistByEmail(String email) {
        return userDao.existsByEmail(email);
    }
}
