package com.acm.service;

import java.util.List;

import com.acm.model.Contact;

public interface ContactService {
    public Contact saveContact(Contact contact);
    public Contact updateContact();
    public List<Contact> getAllContacts();
    public Contact getContactById(String id);
    public void deleteContact(String id);
    public List<Contact> search(String name, String email, String phoneNumber);
    public List<Contact> getByUserId(String id);
    
}
