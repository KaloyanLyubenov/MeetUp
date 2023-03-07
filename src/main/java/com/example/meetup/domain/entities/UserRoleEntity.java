package com.example.meetup.domain.entities;

import com.example.meetup.domain.enums.UserRoleEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_roles")
public class UserRoleEntity extends BaseEntity{

    @Column(name = "user_role")
    private UserRoleEnum userRole;

}
