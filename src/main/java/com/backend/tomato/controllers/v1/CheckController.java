package com.backend.tomato.controllers.v1;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/check")
public class CheckController {
    @GetMapping("/admin")
    public String adminPage(){
        return "Admin Page";
    }
    @GetMapping("/user")
    public String userPage(){
        return "User Page";
    }
}