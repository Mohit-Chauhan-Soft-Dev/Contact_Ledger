package com.acm.service;

import java.util.List;
import java.util.Optional;

import com.acm.model.User;


public interface UserService {
    
    public User saveUser(User user);
    public Optional<User> getUserById(String id);
    public Optional<User> getUserByUserName(String email);
    public User updateUser(User user);
    public void deleteUser(String id);
    public boolean isUserExist(String id);
    public boolean isUserExistByEmail(String email);
    public List<User> getAllUsers();

}
