package com.revature.controllers;

import javax.validation.Valid;

import com.revature.dtos.CredentialsDTO;
import com.revature.dtos.UserDTO;
import com.revature.exceptions.EmailUnavailibleException;
import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.UsernameUnavailibleException;
import com.revature.models.User;
import com.revature.repos.UserRepository;
import com.revature.security.JwtConfig;
import com.revature.security.TokenGenerator;
import com.revature.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

   private UserRepository userRepository;
   private PasswordEncoder encoder;
   private UserService userService;
   private TokenGenerator tokenGenerator;
   private JwtConfig jwtConfig;

    @Autowired
    public AuthController(UserRepository userRepository, PasswordEncoder encoder,UserService userService, TokenGenerator tokenGenerator, JwtConfig jwtConfig) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.userService = userService;
        this.tokenGenerator = tokenGenerator;
        this.jwtConfig = jwtConfig;
    }

    @PostMapping(name = "/login",consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> authenticate(@RequestBody @Valid CredentialsDTO credentials, HttpServletResponse resp) {
        User user = userService.authenticate(credentials.getUsername(), encoder.encode(credentials.getPassword()));
        String jwt = tokenGenerator.createJwt(user);
        resp.setHeader(jwtConfig.getHeader(), jwt);
        return ResponseEntity.ok(user);
    }

    @PostMapping(name = "/register",consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> registerUser(@Valid @RequestBody UserDTO signUpRequest, HttpServletResponse resp) {
        User registerUser = new User();
        registerUser.setUsername(signUpRequest.getUsername());
        registerUser.setPassword(encoder.encode(signUpRequest.getPassword()));
        registerUser.setEmail(signUpRequest.getEmail());
        registerUser.setFirstName(signUpRequest.getFirstName());
        registerUser.setLastName(signUpRequest.getLastName());
        registerUser.setCity(signUpRequest.getCity());
        registerUser.setState(signUpRequest.getState());

        try{
            userService.register(registerUser);
        }catch (UsernameUnavailibleException e){
            return ResponseEntity
                    .badRequest()
                    .body("Error username is taken");
        }
        catch (EmailUnavailibleException e){
            return ResponseEntity
                    .badRequest()
                    .body("Error email is taken");
        }catch (InvalidRequestException e){
            return ResponseEntity
                    .badRequest()
                    .body("Invalid information sent to API");
        }

        CredentialsDTO credentialsDTO = new CredentialsDTO();
        credentialsDTO.setUsername(registerUser.getUsername());
        credentialsDTO.setPassword(registerUser.getPassword());
        return ResponseEntity.ok(authenticate(credentialsDTO,resp));
        }
    }

