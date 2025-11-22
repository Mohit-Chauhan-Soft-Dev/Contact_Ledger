package com.acm.controller;


import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acm.form.ContactForm;
import com.acm.helper.ResourceNotFoundException;
import com.acm.helper.UserLoggedInHelper;
import com.acm.model.Contact;
import com.acm.model.User;
import com.acm.service.ContactService;
import com.acm.service.UserService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMethod;




@Controller
@RequestMapping("/user/contacts")
@NoArgsConstructor
@AllArgsConstructor
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;


    @RequestMapping("/add")
    public String addContact(Model model) {

        ContactForm contactForm = new ContactForm();

        // contactForm.setName("Mohit Thakur");
        // contactForm.setFavorite(true);

        model.addAttribute("contactForm", contactForm);


        return "/user/add_contact";
    }

   


    @RequestMapping(value = "/add", method=RequestMethod.POST)
    public String saveContact(@ModelAttribute ContactForm contactForm, Authentication authentication) {

        UserLoggedInHelper helper = new UserLoggedInHelper();

        String username = helper.getLoggedInUserEmail(authentication);

        System.out.println("Username : " + username);

        User user = userService.getUserByUserName(username).orElseThrow(()-> new ResourceNotFoundException("User not found"));
        
        // Process the form data
        Contact contact = new Contact();

        contact.setId(UUID.randomUUID().toString());
        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setPhone(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setFavorite(contactForm.isFavorite());
        contact.setUser(user);

        System.out.println(contactForm);

        contactService.saveContact(contact);

        return "redirect:/user/contacts/add";
    }
    
    

}
