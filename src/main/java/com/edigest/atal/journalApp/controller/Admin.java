package com.edigest.atal.journalApp.controller;

import com.edigest.atal.journalApp.cache.AppCache;
import com.edigest.atal.journalApp.entity.User;
import com.edigest.atal.journalApp.service.EMailService;
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

    @Autowired
    AppCache appCache;

    @Autowired
    EMailService eMailService;

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

    @GetMapping("clear-app-cache")
    public ResponseEntity<?> clearAppCache() {
        appCache.init();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/send-email")
    public ResponseEntity<?> sendEmail(@RequestBody String body) {
        this.eMailService.sendEmail("atal@live.com", "Testing SpringBoot", body);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
