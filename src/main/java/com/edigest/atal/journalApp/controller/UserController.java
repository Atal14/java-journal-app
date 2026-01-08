package com.edigest.atal.journalApp.controller;

import com.edigest.atal.journalApp.api.response.WeatherResponse;
import com.edigest.atal.journalApp.entity.User;
import com.edigest.atal.journalApp.service.UserService;
import com.edigest.atal.journalApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private WeatherService weatherService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb = userService.findByUserName(userName);
        userInDb.setUserName(user.getUserName());
        userInDb.setPassword(user.getPassword());
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        userService.deleteByUserName(userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/greeting")
    public ResponseEntity<?> greeting() {
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         WeatherResponse weatherResponse = weatherService.getWeather("Lahore");
         String greeting = "";
         if (weatherResponse != null) {
             greeting = ", Weather feels like " + weatherResponse.getCurrent().getFeelsLike();
         }

        
         return  new ResponseEntity<>("Hi " + authentication.getName() + greeting, HttpStatus.OK);
//        return  new ResponseEntity<>("Hi " + this.playWithStrings() + " ", HttpStatus.OK);
    }

    @GetMapping("/sa-users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getUsersBySA();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    //programming tests
    private List<String> thridEle() {
        String[] elements = {"Hi", "Hello", "How", "Atal", "Sohail", "Huzaifa", "Ali", "Bit", "Logix"};

        List<String> finalElements = new ArrayList<>();
        int j = 0;
        for (String element : elements) {
            j++;
            if (j == 3) {
                finalElements.add(element);
                j = 0;
            }
        }

        return finalElements;
    }

    private List<Integer> prime() {
        int[] someNumbers = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19};
        List<Integer> primeNumbers = new ArrayList<>();

        for (int number: someNumbers) {
            int j=0;
            for (int i=1; i<number; i++) {
                if (number % i == 0) {
                    j++;
                }
            }
            if (j == 1) {
                primeNumbers.add(number);
            }
        }
        return primeNumbers;
    }

    private List<Integer> difference() {
        int[] numbersA = {1,2,3,4,5,6};
        int[] numbersB = {2,4,6,8,10};

        List<Integer> differenceNumber = new ArrayList<>();
        for (int secondNum: numbersB) {
            boolean found = false;
            for (int firstNum: numbersA) {
                if (secondNum == firstNum) {
                    found = true;
                    break;
                }
            }
            if (found == false) {
                differenceNumber.add(secondNum);
            }
        }
        return differenceNumber;
    }

    private int fibonaci(int num) {
        int a = 0;
        int b = 1;
        int fib = 0;
        if (num == 1) {
            return a;
        }
        if (num == 2) {
            return b;
        }
        for (int i=3; i<num; i++) {
            fib = a + b;
            a = b;
            b = fib;
        }

        return a + b;
    }

    private List<String> nonRepeating() {
        String[] names = {"Atal", "Sohail", "atal", "sohail"};
        List<String> uniqueNames = new ArrayList<>();
        
        for (String name : names) {
            boolean found = false;

            for (String uniqueName : uniqueNames) {
                if (uniqueName.equalsIgnoreCase(name)) {
                    found = true;
                    break;
                }
            }
            
            if (!found) {
                uniqueNames.add(name);
            }
        }

        return uniqueNames;
    }

    private boolean threeDivisiors(int num) {
        int count = 0;

        for (int i=1; i<=num; i++)  {
            if (num % i == 0) {
                count++;
            }
        }

        return count == 3;
    }

    private List<String> playWithStrings() {
        String[] fruits = {"Apple", "Orange", "Banana"};
        for (String s : fruits) {
            String fruit = s;
            fruit = fruit + "s";
        }
        return Arrays.asList(fruits);
    }

}