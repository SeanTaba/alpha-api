package com.revature.services;


import com.revature.exceptions.*;

import com.revature.exceptions.AuthenticationException;
import com.revature.exceptions.DataSourceException;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.models.Mail;
import com.revature.models.User;
import com.revature.services.MailService;

import com.revature.repos.UserRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserService {

    private UserRepository userRepo;

    private static final  String EMAIL = "email";
    private static final  String USERNAME = "username";

    @Autowired
    public UserService(UserRepository userDao) {
        this.userRepo = userDao;
    }


    //registers newUser. Checks to see if email and uname are available (no user roles implemented)
    public User register(User newUser) throws InvalidRequestException,EmailUnavailibleException,UsernameUnavailibleException {

        isUserValid(newUser);

        if(Boolean.TRUE.equals(userRepo.existsByUsername(newUser.getUsername()))){
            throw new UsernameUnavailibleException();
        }
        if(Boolean.TRUE.equals(userRepo.existsByEmail(newUser.getEmail()))){
            throw new EmailUnavailibleException();
        }
        return userRepo.save(newUser);
    }

    //gets all users from repo
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
    //inverted method that validates all fields of a user using a switch method
    //without passing in an entire user object
    public boolean isValid(String str, String fieldName) {

        if (str == null || str.trim().isEmpty()) return false;

        switch (fieldName) {
            case USERNAME:
                return str.length() < 20;
            case "firstName":
            case "lastName":
                return str.length() < 25;
            case "password":
            case EMAIL:
                return str.length() < 255;
            default:
                return true;
        }

    }



    //checks if all users fields are valid by passing in a user object
    public void isUserValid(User user) throws InvalidRequestException{
        if (user == null) {
            throw new InvalidRequestException("User is null");
        }
        if (!isValid(user.getUsername(), USERNAME)) {
            throw new InvalidRequestException("Invalid username");
        }

        if (!isValid(user.getPassword(), "password")) {
            throw new InvalidRequestException("Invalid password");
        }
        if (!isValid(user.getEmail(), EMAIL)) {
            throw new InvalidRequestException("Invalid Email");
        }
        if (!isValid(user.getFirstName(), "firstName")) {
            throw new InvalidRequestException("Invalid First Name");
        }
        if (!isValid(user.getLastName(), "lastName")){
            throw new InvalidRequestException("Invalid LastName");
        }
    }


//    public Boolean isPasswordSecure(User verifyUser){
//        String password = verifyUser.getPassword();
//        boolean hasLetter = false;
//        boolean hasDigit = false;
//
//        if (password.length() < 8) return false;
//
//        for (char character:password.toCharArray()) {
//            if (Character.isLetter(character)) hasLetter = true;
//            if (Character.isDigit(character)) hasDigit = true;
//            if (hasLetter && hasDigit) return true;
//        }
//
//        return false;
//    }



    //checks username availability
//    @Transactional(propagation = Propagation.SUPPORTS)
    public Boolean isUsernameAvailable(String username) {

        if(!isValid(username, USERNAME)){
            throw new InvalidRequestException("Invalid username");
        }
        try{
            return userRepo.isUsernameAvailable(username);
        }catch(ResourceNotFoundException e){
            throw new DataSourceException(e);
        }
    }

    //checks email availability
//    @Transactional(propagation = Propagation.SUPPORTS)
    public Boolean isEmailAvailable(String email) {
       if(!isValid(email, EMAIL)){

           throw new InvalidRequestException("Invalid email");
       }
       try {
           return userRepo.isEmailAvailable(email);
       }catch(ResourceNotFoundException e){
          throw new DataSourceException(e);
       }
    }


    @Transactional(readOnly = true)
    public User authenticate(String username, String password) throws AuthenticationException {
        User user = null;
        try {
           user = userRepo.findUserByUsernameAndPassword(username, password)
                    .orElseThrow(AuthenticationException::new);

        } catch (ResourceNotFoundException | DataSourceException e) {
            e.printStackTrace();
        }
        return user;
    }

    public User getUserByUsername(String username) throws InvalidRequestException{
        return userRepo.findUserByUsername(username).orElseThrow( () -> new InvalidRequestException("Username not found"));
    }
}
