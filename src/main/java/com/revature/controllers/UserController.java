package com.revature.controllers;


import com.revature.models.User;
import com.revature.repos.LocationRepository;
import com.revature.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class UserController
{
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

    @Autowired
    public UserController(UserRepository userRepository, LocationRepository locationRepository)
    {
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
    }

    @RequestMapping("/getUserByEmail")
    public User getUserById(@RequestParam String uem)
    {
        return userRepository.findUserByEmail(uem);
    }

    @RequestMapping("/getUserByUsername")
    public User getUserByUsername(@RequestParam String un)
    {
        return userRepository.findUserByUsername(un);
    }

    @PostMapping(value = "/validate",
                produces = MediaType.APPLICATION_JSON_VALUE,
                consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> validateUser(@RequestBody User newUser)
    {
        if (userRepository.findUserByUsername(newUser.getUsername()) != null)
        {
            return new ResponseEntity<>("username", HttpStatus.CONFLICT);
        } else if (userRepository.findUserByEmail(newUser.getEmail()) != null)
        {
            return new ResponseEntity<>("email",HttpStatus.CONFLICT);
        } else if (locationRepository.findLocationByCountryAndState("usa", newUser.getState()).isEmpty())
        {
            return new ResponseEntity<>("state", HttpStatus.NOT_ACCEPTABLE);
        } else if (locationRepository.findLocationByCountryAndStateAndCity("usa", newUser.getState(), newUser.getCity()).isEmpty())
        {
            return new ResponseEntity<>("city", HttpStatus.NOT_ACCEPTABLE);
        } else
        {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PostMapping(value = "/register",
                 produces = MediaType.APPLICATION_JSON_VALUE,
                 consumes = MediaType.APPLICATION_JSON_VALUE)
    public User registerUser(@RequestBody User newUser)
    {
        return userRepository.save(newUser);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }






}
