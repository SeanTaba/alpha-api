package com.revature.controllers;


import com.revature.models.User;
import com.revature.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
        // TODO need to fix to properly handle optionals
        return userRepository.findUserByEmail(uem).get();
    }

    @RequestMapping("/getUserByUsername")
    public User getUserByUsername(@RequestParam String un)
    {
        // TODO need to fix to properly handle optionals
        return userRepository.findUserByUsername(un).get();
    }
// Use @PreAuthorize with Role to select who has access to a specific command
//    @PreAuthorize("hasRole('anyRole')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllUsers()
    {
//        Use to get current users details
//        UserDetails userDetails =
//                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userRepository.findAll();
    }






}
