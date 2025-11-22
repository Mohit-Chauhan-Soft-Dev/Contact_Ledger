package com.acm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.acm.model.User;

public interface UserRepo extends JpaRepository<User, String>{
    public Optional<User> findByEmail(String email);
}
