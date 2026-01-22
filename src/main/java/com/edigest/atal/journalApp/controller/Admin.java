package com.edigest.atal.journalApp.controller;

import com.edigest.atal.journalApp.cache.AppCache;
import com.edigest.atal.journalApp.dto.BasicUserDTO;
import com.edigest.atal.journalApp.entity.User;
import com.edigest.atal.journalApp.service.EMailService;
import com.edigest.atal.journalApp.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin")
@Tag(name = "Admin APIs")
public class Admin {

    @Autowired
    UserService userService;

    @Autowired
    AppCache appCache;

    @Autowired
    EMailService eMailService;

    @Operation(summary = "Get all users along with their journal Entries")
    @GetMapping("all-users")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.getAll();
        if (!users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("create-admin")
    public ResponseEntity<?> createAdminUser(@RequestBody BasicUserDTO user) {
        User adminUser = new User();
        adminUser.setUserName(user.getUserName());
        adminUser.setPassword(user.getPassword());
        adminUser.setEmail(user.getEmail());
        adminUser.setSentimentAnalysis(user.isSentimentAnalysis());
        User createdUser = userService.createAdminUser(adminUser);
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
