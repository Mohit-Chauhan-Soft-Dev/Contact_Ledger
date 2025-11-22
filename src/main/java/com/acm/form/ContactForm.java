package com.acm.form;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContactForm {
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private MultipartFile contactPic;
    private String description;
    private boolean favorite;
    
}
