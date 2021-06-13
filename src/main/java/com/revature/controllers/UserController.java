package com.revature.controllers;

import com.revature.exceptions.ResourceNotFoundException;
import com.revature.models.User;
import com.revature.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


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

//    //this will be hit only when weather change == truthy on the UI side
//    @PostMapping(name="/sendWeatherUpdate",consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
//    public ResponseEntity<EmailInfoDTO> sendWeatherUpdate ( @RequestBody EmailInfoDTO emailInfo) {
//        Mail mail = new Mail();
//        MailServiceImpl mailService = new MailServiceImpl();
////        if (req.getHeader("Authorization") != null ){
//            mail.setMailFrom("AlphaCast");
//            mail.setMailTo(emailInfo.getUserEmail());
//            mail.setMailSubject("AlphaCast - Weather Update");
//            mail.setMailContent(emailInfo.getEmailContent());
//            try{
//                mailService.sendEmail(mail);
//            }catch (Exception e){
//                e.printStackTrace();
//                return ResponseEntity.ok(emailInfo);
//            }
//
////        }
//
//
//        return ResponseEntity.ok(emailInfo);
//
//    }


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
