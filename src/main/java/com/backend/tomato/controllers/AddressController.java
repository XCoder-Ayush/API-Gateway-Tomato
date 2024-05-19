package com.backend.tomato.controllers;

import com.backend.tomato.entitites.Address;
import com.backend.tomato.entitites.User;
import com.backend.tomato.services.AddressService;
import com.backend.tomato.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
@CrossOrigin("*")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<Address>> fetchAddressesByUserId(@PathVariable("userId") String userId) {
        try {
            List<Address> addressList = this.addressService.fetchAddressesByUserId(userId);
            return new ResponseEntity<>(addressList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Address> saveAddress(@RequestBody Address address,@PathVariable("userId") String userId){
        try{
            User user=this.userService.fetchUserById(userId);
            address.setUser(user);
            Address savedAddress =  this.addressService.saveAddress(address);

            return new ResponseEntity<>(savedAddress,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
