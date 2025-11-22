package com.acm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.acm.service.implementation.CustomUserDetailsService;

@Configuration
public class SecurityConfig {

    // in memory service
    // @Bean
    // public UserDetailsService userDetailsService(){

    // UserDetails user1 =
    // User.withDefaultPasswordEncoder().username("Mohit").password("Mohit@1234").roles("USER").build();
    // UserDetails user2 =
    // User.withDefaultPasswordEncoder().username("Rohit").password("Rohit@1234").roles("USER").build();

    // var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user1,
    // user2);
    // return inMemoryUserDetailsManager;
    // }

    // private UserDetails userDetails;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private OAuthAuthenticationSuccessHandler successHandler;

    @SuppressWarnings("deprecation")
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        
        return daoAuthenticationProvider;

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {

        security.authorizeHttpRequests(authorize -> {

            // authorize.requestMatchers("/", "/login", "/signup", "/home", "/contact","/services").permitAll();
            // authorize.requestMatchers("/user/**").authenticated();

            authorize.requestMatchers("/user/**").authenticated().anyRequest().permitAll();

        });

        // security.formLogin(Customizer.withDefaults());

        security.formLogin(formLogin -> {

            formLogin

                    .loginPage("/login")
                    .loginProcessingUrl("/authenticate")
                    .defaultSuccessUrl("/user/profile", true)
                    .failureUrl("/login?error=true")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .permitAll();

            // formLogin.successForwardUrl("/user/dashboard");
            // formLogin.failureForwardUrl("/login?error=true");

            // formLogin.failureHandler(new AuthenticationFailureHandler() {
            // @Override
            // public void onAuthenticationFailure(HttpServletRequest request,
            // HttpServletResponse response,
            // AuthenticationException exception) throws IOException, ServletException {
            // throw new UnsupportedOperationException("Unimplemented method
            // 'onAuthenticationFailure'");
            // }
            // });

            // formLogin.successHandler(new AuthenticationSuccessHandler() {
            // @Override
            // public void onAuthenticationSuccess(HttpServletRequest request,
            // HttpServletResponse response,
            // Authentication authentication) throws IOException, ServletException {
            // throw new UnsupportedOperationException("Unimplemented method
            // 'onAuthenticationSuccess'");
            // }

            // });

        });

        security.csrf(csrf -> csrf.disable());
        security.logout(logoutForm -> {
            logoutForm.logoutUrl("/logout");
            logoutForm.logoutSuccessUrl("/login?logout=true");
        });

        // Oauth2 Client Configuration
        security.oauth2Login(oauth-> {

            oauth.loginPage("/login");
            // oauth.defaultSuccessUrl("/user/dashboard", true);
            oauth.failureUrl("/login?error=true");
            oauth.successHandler(successHandler);

        });

        return security.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }

}
