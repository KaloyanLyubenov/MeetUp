package com.example.meetup.domain.dto;

import com.example.meetup.domain.entities.MeetEntity;
import com.example.meetup.domain.entities.UserRoleEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserModel {

    private Long id;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private List<MeetModel> announcedMeets = new ArrayList<>();
    private List<UserRoleEntity> roles = new ArrayList<>();

    private PictureModel profilePicture;

    public UserModel setId(Long id) {
        this.id = id;
        return this;
    }

    public UserModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserModel setAnnouncedMeets(List<MeetModel> announcedMeets) {
        this.announcedMeets = announcedMeets;
        return this;
    }

    public UserModel setRoles(List<UserRoleEntity> roles) {
        this.roles = roles;
        return this;
    }

    public UserModel setProfilePicture(PictureModel profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }
}
