package com.acm.config;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.acm.helper.AppConstants;
import com.acm.model.Provider;
import com.acm.model.User;
import com.acm.repository.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepo userRepo;

    Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        logger.info("OAuthAuthenticationSuccessHandler");

        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;

        // Identify the provider
        String provider = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();

        oAuth2User.getAttributes().forEach((key, value) -> {
            logger.info(" {} : {} ", key, value);
        });

        User appUser = new User();

        appUser.setId(UUID.randomUUID().toString());
        appUser.setEnabled(true);
        appUser.setRoleList(AppConstants.ROLES);
        appUser.setEmailVerified(true);
        appUser.setPassword(anotherPasswordEncoder().encode("@#$%^&*()")); // Placeholder password

        if (provider.equalsIgnoreCase("google")) {

            appUser.setName(oAuth2User.getAttribute("name"));
            appUser.setEmail(oAuth2User.getAttribute("email"));
            appUser.setProfilePic(oAuth2User.getAttribute("picture"));
            appUser.setProvider(Provider.GOOGLE);
            appUser.setProviderUserId(oAuth2User.getName());
            appUser.setAbout("This is created via Google.");

        } else if (provider.equalsIgnoreCase("github")) {
            appUser.setName(oAuth2User.getAttribute("login"));
            appUser.setEmail(oAuth2User.getAttribute("login") + "@gmail.com");
            appUser.setProfilePic(oAuth2User.getAttribute("avatar_url"));
            appUser.setProvider(Provider.GITHUB);
            appUser.setProviderUserId(oAuth2User.getName());
            appUser.setAbout("This is created via GitHub.");
        } else {
            logger.error("Provider not supported: {}", provider);
            throw new IllegalArgumentException("Unsupported OAuth provider: " + provider);
        }

        User user = userRepo.findByEmail(appUser.getEmail()).orElse(null);

        if (user == null) {
            userRepo.save(appUser);
            logger.info("User saved successfully with email : {}", appUser.getEmail());
        }

        
        // appUser.setName(user.getAttribute("name"));
        // appUser.setEmail(user.getAttribute("email"));
        // appUser.setProfilePic(user.getAttribute("picture"));
        // appUser.setProvider(Provider.GOOGLE);
        // appUser.setProviderUserId(user.getName());
        // appUser.setAbout("This ia created via Google.");

        // another way to get user details
        // // User appUser = (User)
        // // userRepo.findByEmail(user.getAttribute("email")).orElse(null);

        // // if (appUser == null) {

        // // appUser.setName(user.getAttribute("name"));
        // // appUser.setEmail(user.getAttribute("email"));
        // // appUser.setPassword("@#$%^&*()");
        // // appUser.setProfilePic(user.getAttribute("picture"));
        // // appUser.setId(UUID.randomUUID().toString());
        // // appUser.setProvider(Provider.GOOGLE);
        // // appUser.setEnabled(true);
        // // appUser.setRoleList(AppConstants.ROLES);
        // // appUser.setProviderUserId(user.getName());
        // // appUser.setAbout("This ia created via Google.");

        // // userRepo.save(appUser);

        // // logger.info("user saved ...");

        // // }

        // // User u = userRepo.findByEmail(user.getId()).orElseThrow(() -> new
        // // ResourceNotFoundException("User not found..."));

        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
    }

    @Bean
    public PasswordEncoder anotherPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
