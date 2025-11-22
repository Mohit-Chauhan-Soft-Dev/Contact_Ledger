package com.acm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.acm.model.Contact;
import com.acm.model.User;

@Repository
public interface ContactRepo extends JpaRepository<Contact, String> {
    // Custom finder method
    public List<Contact> findByUser(User user);

    // Custom query method
    @Query("SELECT c FROM Contact c WHERE c.user.id = :userId")
    public List<Contact> findByUserId(@Param("userId") String userId);
}
