package com.example.meetup.domain.entities;

import com.example.meetup.domain.enums.UserRoleEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Builder
@Table(name = "users")
public class UserEntity extends BaseEntity{

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<MeetEntity> announcedMeets = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<UserRoleEntity> roles = new ArrayList<>();

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "picture_id")
    private PictureEntity profilePicture;

    public UserEntity setProfilePicture(PictureEntity profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }

    public UserEntity setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserEntity setAnnouncedMeets(List<MeetEntity> announcedMeets) {
        this.announcedMeets = announcedMeets;
        return this;
    }

    public UserEntity setRoles(List<UserRoleEntity> roles) {
        this.roles = roles;
        return this;
    }

    public Boolean isAdmin(){
        return this.getRoles().stream()
                .anyMatch(role -> role.getUserRole() == UserRoleEnum.ADMIN);
    }
    public Boolean isModerator(){
        return this.getRoles().stream()
                .anyMatch(role -> role.getUserRole() == UserRoleEnum.MODERATOR);
    }



}
