package com.edigest.atal.journalApp.controller;

import com.edigest.atal.journalApp.entity.User;
import com.edigest.atal.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class Public {

    @Autowired
    private UserService userService;

    @PostMapping("/create-user")
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }
}
