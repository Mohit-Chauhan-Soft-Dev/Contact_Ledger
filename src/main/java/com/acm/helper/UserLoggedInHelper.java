package com.acm.helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
// import org.springframework.security.oauth2.core.user.OAuth2User;

public class UserLoggedInHelper {

    private Logger logger = LoggerFactory.getLogger(UserLoggedInHelper.class);
    public String getLoggedInUserEmail(Authentication authentication) {

        if (authentication instanceof OAuth2AuthenticationToken) {

            var token = (OAuth2AuthenticationToken) authentication;
            var regId = token.getAuthorizedClientRegistrationId();
            var oAuthUser = (DefaultOAuth2User) authentication.getPrincipal();

            String username = "";

            if(regId.equalsIgnoreCase("google")) {
                logger.info("Getting email from Google ...");
                username = oAuthUser.getAttribute("email").toString();
            } else if (regId.equalsIgnoreCase("github")) {
                logger.info("Getting email from GitHub ...");
                username = oAuthUser.getAttribute("login").toString() + "@gmail.com";
            }

            return username;

        } 

        logger.info("Getting email from local db ...");
        return authentication.getName();

    }

}
