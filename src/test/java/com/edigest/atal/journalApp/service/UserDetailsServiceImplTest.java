package com.edigest.atal.journalApp.service;

import com.edigest.atal.journalApp.entity.User;
import com.edigest.atal.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

public class UserDetailsServiceImplTest {

    // @InjectMocks
    // private UserDetailsServiceImpl userDetailsService;

    // @Mock
    // private UserRepository userRepository;

    // @BeforeEach
    // void setUp() {
    //     MockitoAnnotations.initMocks(this);
    // }

    // @Test
    // void loadUserByUserNameTest() {
    //     User user = new User("Atal", "something");
    //     user.setRoles(new ArrayList<>());
    //     when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(user);
    //     UserDetails mockedUser = userDetailsService.loadUserByUsername("Atal");
    //     Assertions.assertNotNull(mockedUser);
    // }
}
