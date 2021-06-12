package com.revature.controllers;

import com.revature.exceptions.ResourceNotFoundException;
import com.revature.models.User;
import com.revature.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("users")
public class UserController
{

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @RequestMapping("/getUserByEmail")
    public User getUserById(@RequestParam String uem)
    {
        return userRepository.findUserByEmail(uem).orElseThrow(ResourceNotFoundException::new);
    }

    @RequestMapping("/getUserByUsername")
    public User getUserByUsername(@RequestParam String un)
    {
        return userRepository.findUserByUsername(un).orElseThrow(ResourceNotFoundException::new);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }


}
