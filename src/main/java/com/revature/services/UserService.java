package com.revature.services;

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
}
