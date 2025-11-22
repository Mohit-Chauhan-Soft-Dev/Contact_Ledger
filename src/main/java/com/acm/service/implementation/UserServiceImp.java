package com.acm.service.implementation;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.acm.helper.AppConstants;
import com.acm.helper.ResourceNotFoundException;
import com.acm.model.User;
import com.acm.repository.UserRepo;
import com.acm.service.UserService;

@Service
public class UserServiceImp implements UserService{

    @Autowired
    private UserRepo userRepo;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired 
    private PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {

        // generate id
        String id = UUID.randomUUID().toString();
        logger.info("Id generated ...");

        // set id
        user.setId(id);
        logger.info("Id given to user ...");

        // set role
        user.setRoleList(AppConstants.ROLES);
        logger.info("Roles given to user ...");

        // enable encoding
        logger.info("Enabling password encoding ...");
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // save user to db
        User savedUser = userRepo.save(user);
        logger.info("User saved to db ...");
        
        return savedUser;

    }

    

    @Override
    public Optional<User> getUserById(String id) {
        return userRepo.findById(id);
    }

    @Override
    public User updateUser(User user) {
        User u = userRepo.findById(user.getId()).orElseThrow(() -> new ResourceNotFoundException("User not found..."));
        return u != null ? userRepo.save(user) : null;
    }

    @Override
    public void deleteUser(String id) {
        userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found..."));
        userRepo.deleteById(id);
    }

    @Override
    public boolean isUserExist(String id) {
        User user = userRepo.findById(id).orElse(null);
        return user != null;
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        User user = userRepo.findByEmail(email).orElse(null);
        return user != null;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }



    @Override
    public Optional<User> getUserByUserName(String email) {
        return userRepo.findByEmail(email);
    }

}
