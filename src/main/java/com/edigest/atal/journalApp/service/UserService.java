package com.edigest.atal.journalApp.service;

import com.edigest.atal.journalApp.entity.User;
import com.edigest.atal.journalApp.repository.UserCriteriaRepository;
import com.edigest.atal.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserCriteriaRepository userCriteriaRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("USER"));
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User createAdminUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("ADMIN"));
        return userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId id) {
        return userRepository.findById(id);
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public User deleteByUserName(String userName) {
        return userRepository.deleteByUserName(userName);
    }

    public void deleteById(ObjectId id) {
        userRepository.deleteById(id);
    }

    public List<User> getUsersBySA() {
        return userCriteriaRepository.getUsersForSA();
    }
}
