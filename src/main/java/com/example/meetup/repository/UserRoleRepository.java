package com.example.meetup.repository;

import com.example.meetup.domain.entities.UserRoleEntity;
import com.example.meetup.domain.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
    Optional<UserRoleEntity> findUserRoleEntitiesByUserRole(UserRoleEnum role);

    Optional<UserRoleEntity> findByUserRole(UserRoleEnum role);
}
