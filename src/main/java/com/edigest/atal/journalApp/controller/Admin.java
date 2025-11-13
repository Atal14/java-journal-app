package com.edigest.atal.journalApp.controller;

import com.edigest.atal.journalApp.entity.User;
import com.edigest.atal.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin")
public class Admin {

    @Autowired
    UserService userService;

    @GetMapping("all-users")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.getAll();
        if(!users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("create-admin")
    public ResponseEntity<?> createAdminUser(@RequestBody User user) {
        User createdUser = userService.createAdminUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
}
