package com.backend.tomato.services;

import com.backend.tomato.dao.UserDao;
import com.backend.tomato.entitites.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        DB Logic
        User user=userDao.findByEmail(username).orElseThrow(()->new RuntimeException("User Not Found In Our DB"));
        return user;
    }
}
