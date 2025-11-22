package com.acm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.acm.form.UserForm;
import com.acm.helper.Message;
import com.acm.helper.MessageType;
// import com.acm.helper.SessionHelper;
// import com.acm.model.Provider;
import com.acm.model.User;
import com.acm.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class PageController {

    // This method handles the base URL
    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }

    // This method handles the home page request
    @GetMapping("/home") 
    public String home(Model model) {
        model.addAttribute("projectName", "Contact_Ledger");
        model.addAttribute("projectDescription", "A simple contact management application.");
        model.addAttribute("githubLink","https://github.com/Mohit-Chauhan-Soft-Dev");
        return "home";
    }

    // This method handles the about page request
    @GetMapping("/about")
    public String about() {
        return "about";
    }
    
    // This method handles the services page request
    @GetMapping("/services")
    public String services() {
        return "services";
    }

    // This method handles the contact page request
    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }
    
    // This method handles the signup page request
    @GetMapping("/signup")
    public String register(Model model){
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        return "signup";
    }

    @Autowired
    private UserService userService;

    // This method processes the signup form for submission
    @PostMapping("/do_signup")
    public String doRegister(@Valid @ModelAttribute UserForm userForm, BindingResult result, HttpSession session) {

        System.out.println("processing registration...");

        if(result.hasErrors()) {
            return "signup";
        }
        
        User user = new User();
        user.setName(userForm.getName());
        user.setEmail((userForm.getEmail()));
        user.setPassword(userForm.getPassword());
        user.setPhone(userForm.getPhone());
        user.setAbout(userForm.getAbout());
        // user.setProvider(Provider.SELF);

        User u = userService.saveUser(user);
        System.out.println("User added to db : " + u);

        Message message = Message.builder().content("Registration Successfull !!").type(MessageType.green).build();

        session.setAttribute("message", message);
        return "redirect:/signup";
        
    }


    // This method handles the login page request
    @GetMapping("/login")
    public String login(){
        return "login";
    }
}

