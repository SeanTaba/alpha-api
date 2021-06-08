package com.revature.controllers;


import com.revature.models.User;
import com.revature.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class UserController
{
    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository)
    {
        this.userRepository = userRepository;
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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }






}
