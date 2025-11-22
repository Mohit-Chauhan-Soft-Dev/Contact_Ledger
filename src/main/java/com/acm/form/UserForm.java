package com.acm.form;

import org.springframework.stereotype.Component;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserForm {

    @NotBlank(message = "Name should not be blank !!")
    @Size(min = 3, max = 20, message = "Name should contain 3 to 20 characters !!")
    private String name;

    @Email(message = "Invalid Email Address !!")
    private String email;

    @NotBlank(message = "Password should not be blank !!")
    @Size(min = 6, message = "Password must contain minimum 6 characters !!")
    private String password;

    @Size(min = 10, max = 10, message = "Phone number must contain 10 digits !!")
    private String phone;

    private String about;
}
