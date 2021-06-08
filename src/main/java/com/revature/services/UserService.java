package com.revature.services;

import com.revature.exceptions.AuthenticationException;
import com.revature.exceptions.DataSourceException;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.models.User;
import com.revature.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    private UserRepository userRepo;

    @Autowired
    public UserService(UserRepository userDao){
        this.userRepo = userDao;
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
