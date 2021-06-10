package com.revature.controllers;

import com.revature.models.Mail;
import com.revature.models.User;
import com.revature.repos.LocationRepository;
import com.revature.repos.UserRepository;
import com.revature.services.MailService;
import com.revature.services.MailServiceImpl;
import com.revature.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.http.ResponseEntity;

import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.revature.dtos.UserDTO;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping("/users")
public class UserController
{

    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private MailServiceImpl mailService;

    @Autowired
    public UserController(UserRepository userRepository, LocationRepository locationRepository) {
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
    }

    //this will be hit only when weather change == truthy on the UI side
    @GetMapping(name="/sendWeatherUpdate",consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> sendWeatherUpdate (@Valid @RequestBody String email, @Valid @RequestBody String content, HttpServletResponse resp) {
        Mail mail = new Mail();
        MailServiceImpl mailService = new MailServiceImpl();
        mail.setMailFrom("AlphaCast");
        mail.setMailTo(email);
        mail.setMailSubject("AlphaCast - Weather Update");
        mail.setMailContent(content);
        try{
            mailService.sendEmail(mail);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (resp.getStatus() == 401){
            System.out.println("not signed in");
        }

        return ResponseEntity.ok("email sent");

    }

//    @RequestMapping("/getUserByEmail")
//    public User getUserById(@RequestParam String uem)
//    {
//        return userRepository.findUserByEmail(uem);
//    }
//
//    @RequestMapping("/getUserByUsername")
//    public User getUserByUsername(@RequestParam String un)
//    {
//        return userRepository.findUserByUsername(un);
//    }
//
//    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<User> getAllUsers()
//    {
//        return userRepository.findAll();
//    }
//
//    @PostMapping(value="/users",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
//    public User register(@RequestBody @Valid User newUser)
//    {
//
//        return userRepository.registerUser(newUser);
//    }



}
