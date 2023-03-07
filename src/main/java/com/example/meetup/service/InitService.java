package com.example.meetup.service;

import com.example.meetup.domain.entities.MeetEntity;
import com.example.meetup.domain.entities.UserEntity;
import com.example.meetup.domain.entities.UserRoleEntity;
import com.example.meetup.domain.enums.UserRoleEnum;
import com.example.meetup.repository.UserRepository;
import com.example.meetup.repository.UserRoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InitService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public InitService(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder, @Value("${app.default.password}") String defaultPassword) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init(){
        initRoles();
        initUsers();
    }

    private void initRoles() {
        if(userRepository.count() == 0){
            UserRoleEntity moderatorRole = new UserRoleEntity(UserRoleEnum.MODERATOR);
            UserRoleEntity adminRole = new UserRoleEntity(UserRoleEnum.ADMIN);

            userRoleRepository.save(moderatorRole);
            userRoleRepository.save(adminRole);
        }
    }

    private void initUsers(){
        if(userRepository.count() == 0){
            initAdmin();
            initModerator();
            initNormalUser();
        }
    }

    private void initAdmin(){
        UserEntity adminUser = new UserEntity("admin",
                passwordEncoder.encode("topsecret"),
                "admin@example.com",
                "admin",
                "admin",
                null,
                userRoleRepository.findAll());

        userRepository.save(adminUser);
    }

    private void initModerator(){
        UserRoleEntity moderatorRole = userRoleRepository.
                findUserRoleEntitiesByUserRole(UserRoleEnum.MODERATOR).orElseThrow();

        UserEntity moderatorUser = new UserEntity("moderator",
                passwordEncoder.encode("topsecret"),
                "mod@example.com",
                "mod",
                "mod",
                null,
                List.of(moderatorRole));

        userRepository.save(moderatorUser);
    }

    private void initNormalUser(){
        UserEntity moderatorUser = new UserEntity("user",
                passwordEncoder.encode("topsecret"),
                "user@example.com",
                "Normal",
                "user",
                null,
                null);

        userRepository.save(moderatorUser);
    }
}
