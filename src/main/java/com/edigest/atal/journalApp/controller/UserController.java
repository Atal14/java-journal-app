package com.edigest.atal.journalApp.controller;

import com.edigest.atal.journalApp.api.response.WeatherResponse;
import com.edigest.atal.journalApp.dto.BasicUserDTO;
import com.edigest.atal.journalApp.entity.User;
import com.edigest.atal.journalApp.service.UserService;
import com.edigest.atal.journalApp.service.WeatherService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Tag(name = "User APIs", description = "Read, Update & Delete User")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private WeatherService weatherService;

    @Operation(summary = "Can only update account from which user is logged in")
    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody BasicUserDTO user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb = userService.findByUserName(userName);
        userInDb.setUserName(user.getUserName());
        userInDb.setPassword(user.getPassword());
        userService.saveUser(userInDb);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Can only delete account from which user is logged in")
    @DeleteMapping("/{userName}")
    public ResponseEntity<String> deleteUserByUserName(@PathVariable String userName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticationUserName = authentication.getName();
        // System.out.println(authentication.getCredentials());
        // if (authenticationUserName.toLowerCase() != userName.toLowerCase()) {
        //     return new ResponseEntity<String>("You don't have permission to delete this user", HttpStatus.FORBIDDEN);
        // }
        userService.deleteByUserName(authenticationUserName);
        return new ResponseEntity<String>("User deleted successfully", HttpStatus.OK);
    }

    @Operation(summary = "Get Weather of a city")
    @GetMapping("/{city}")
    public ResponseEntity<?> greeting(@PathVariable String city) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse = weatherService.getWeather(city);
        String greeting = "";
        if (weatherResponse != null) {
            greeting = ", Weather feels like " + weatherResponse.getCurrent().getFeelsLike();
        }
        return new ResponseEntity<>("Hi " + authentication.getName() + greeting, HttpStatus.OK);
    }

    @Operation(summary = "Get all users which have opted for sentiment analysis")
    @GetMapping("/sa-users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getUsersBySA();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

}