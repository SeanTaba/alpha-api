package com.revature.controllers;


import com.revature.models.User;
import com.revature.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @RequestMapping("/getUserById")
    public User getUserById(@RequestParam int uid)
    {
        return userRepository.findUserById(uid);
    }

    @RequestMapping("/getUserByUn")
    public User getUserByUsername(@RequestParam String uun)
    {
        return userRepository.findUserByUsername(uun);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }






}
