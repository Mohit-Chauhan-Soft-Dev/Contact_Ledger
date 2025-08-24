package com.acm.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.acm.helper.UserLoggedInHelper;
import com.acm.model.User;
import com.acm.service.UserService;

@ControllerAdvice
public class RootController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void addLoggedInInfo(Model model, Authentication authentication) {

        if(authentication == null) {
            return;
        }

        UserLoggedInHelper helper = new UserLoggedInHelper();
        String username = helper.getLoggedInUserEmail(authentication);

        // Fetch user by email
        User user = userService.getUserByUserName(username).orElse(null);

        if(user == null) {
            logger.info("User did not login");
        } else {
            model.addAttribute("loggedInUser", user);
            logger.info("User logged in with email : {}", username);
        }
        
    }
    

}
