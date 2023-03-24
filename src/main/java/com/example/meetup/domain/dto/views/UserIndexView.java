package com.example.meetup.domain.dto.views;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserIndexView {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private Boolean isModerator;
    private Boolean isAdmin;
    private String profPicUrl;

    public UserIndexView setId(Long id) {
        this.id = id;
        return this;
    }

    public UserIndexView setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserIndexView setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserIndexView setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserIndexView setModerator(Boolean moderator) {
        isModerator = moderator;
        return this;
    }

    public UserIndexView setAdmin(Boolean admin) {
        isAdmin = admin;
        return this;
    }

    public UserIndexView setProfPicUrl(String profPicUrl) {
        this.profPicUrl = profPicUrl;
        return this;
    }
}
