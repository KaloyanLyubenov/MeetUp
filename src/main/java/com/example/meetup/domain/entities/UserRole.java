package com.example.meetup.domain.entities;

import com.example.meetup.domain.enums.UserRoleEnums;
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
public class UserRole extends BaseEntity{

    @Column(name = "user_role")
    private UserRoleEnums userRole;

}
