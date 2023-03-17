package com.example.meetup.domain.dto;

import com.example.meetup.domain.enums.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleModel {

    private Long id;
    private UserRoleEnum role;

    public UserRoleModel setId(Long id) {
        this.id = id;
        return this;
    }

    public UserRoleModel setRole(UserRoleEnum role) {
        this.role = role;
        return this;
    }
}
