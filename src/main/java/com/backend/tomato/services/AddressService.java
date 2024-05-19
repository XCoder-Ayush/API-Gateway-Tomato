package com.backend.tomato.services;

import com.backend.tomato.dao.AddressDao;
import com.backend.tomato.entitites.Address;

import com.backend.tomato.entitites.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private UserService userService;

    public List<Address> fetchAddressesByUserId(String userId) {
        User user=this.userService.fetchUserById(userId);
        return this.addressDao.findByUser(user);
    }

    public Address saveAddress(Address address) {
        return this.addressDao.save(address);
    }
}