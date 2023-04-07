package com.example.meetup.domain.dto.binding;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserEditDTO {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;

    public UserEditDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public UserEditDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserEditDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserEditDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserEditDTO setEmail(String email) {
        this.email = email;
        return this;
    }
}
