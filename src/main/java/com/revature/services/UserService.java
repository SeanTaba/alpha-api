package com.revature.services;


import com.revature.exceptions.*;

import com.revature.exceptions.AuthenticationException;
import com.revature.exceptions.DataSourceException;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.models.User;

import com.revature.repos.UserRepository;
import com.revature.models.User;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {
    private UserRepository userRepo;

    @Autowired
    public UserService(UserRepository userDao) {
        this.userRepo = userDao;
    }


    //registers newUser. Checks to see if email and uname are available (no user roles implemented)
    public User register(User newUser) throws InvalidRequestException,EmailUnavailibleException,UsernameUnavailibleException {

        isUserValid(newUser);

        if(userRepo.existsByUsername(newUser.getUsername())){
            throw new UsernameUnavailibleException();
        }
        if(userRepo.existsByEmail(newUser.getEmail())){
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

        if (str == null || str.trim().isEmpty()) return true;

        switch (fieldName) {
            case "username":
                return str.length() > 20;
            case "firstName":
            case "lastName":
                return str.length() > 25;
            case "password":
            case "email":
                return str.length() > 255;
            default:
                return true;
        }

    }

    //checks if all users fields are valid by passing in a user object
    public void isUserValid(User user) throws InvalidRequestException{
        if (user == null) {
            throw new InvalidRequestException("User is null");
        }
        if (!isValid(user.getUsername(), "username")) {
            throw new InvalidRequestException("Invalid username");
        }

        if (!isValid(user.getPassword(), "password")) {
            throw new InvalidRequestException("Invalid password");
        }
        if (!isValid(user.getEmail(), "email")) {
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
    @Transactional(propagation = Propagation.SUPPORTS)
    public Boolean isUsernameAvailable(String username) {

        if(!isValid(username, "username")){
            throw new InvalidRequestException("Invalid username");
        }
        try{
            return userRepo.isUsernameAvailable(username);
        }catch(Exception e){
            throw new DataSourceException(e);
        }
    }

    //checks email availability
    @Transactional(propagation = Propagation.SUPPORTS)
    public Boolean isEmailAvailable(String email) {
       if(!isValid(email, "email")){
           throw new InvalidRequestException("Invalid email");
       }
       try {
           return userRepo.isEmailAvailable(email);
       }catch(Exception e){
           if (e instanceof ResourceNotFoundException) {
               throw e;
           }
           throw new DataSourceException(e);
       }
    }


    @Transactional(readOnly = true)
    public User authenticate(String username, String password) throws AuthenticationException {

        try {
            return userRepo.findUserByUsernameAndPassword(username, password)
                    .orElseThrow(AuthenticationException::new);
        } catch (Exception e) {
            if (e instanceof ResourceNotFoundException) throw e;
            throw new DataSourceException(e);
        }

    }
}
