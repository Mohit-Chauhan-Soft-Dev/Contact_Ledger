package com.acm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "social_link")
@Table(name = "social_links")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SocialLink {

    @Id 
    private String id;
    private String link;
    private String title;

    @ManyToOne
    private Contact contact;

}
