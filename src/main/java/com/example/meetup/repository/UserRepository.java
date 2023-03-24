package com.example.meetup.repository;

import com.example.meetup.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findUserEntityByUsername(String username);

    @Query("SELECT u FROM UserEntity u JOIN u.announcedMeets m WHERE m.id = :meetId")
    UserEntity findByMeeId(@Param("meetId") Long meetId);

}
