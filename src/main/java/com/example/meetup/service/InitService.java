package com.example.meetup.service;

import com.example.meetup.domain.entities.UserEntity;
import com.example.meetup.domain.entities.UserRoleEntity;
import com.example.meetup.domain.enums.UserRoleEnum;
import com.example.meetup.repository.UserRepository;
import com.example.meetup.repository.UserRoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InitService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public InitService(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
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
        if(userRoleRepository.count() == 0){
            var moderatorRole = new UserRoleEntity(UserRoleEnum.MODERATOR);
            var adminRole = new UserRoleEntity(UserRoleEnum.ADMIN);

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
        var adminUser = new UserEntity()
                .setUsername("admin")
                .setPassword(passwordEncoder.encode("topsecret"))
                .setEmail("admin@example.com")
                .setFirstName("admin")
                .setLastName("admin")
                .setRoles(userRoleRepository.findAll());
        userRepository.save(adminUser);
    }

    private void initModerator(){
        var moderatorRole = userRoleRepository.
                findUserRoleEntitiesByUserRole(UserRoleEnum.MODERATOR).orElseThrow();

        var moderatorUser = new UserEntity()
                .setUsername("moderator")
                .setPassword(passwordEncoder.encode("topsecret"))
                .setEmail("mod@example.com")
                .setFirstName("mod")
                .setLastName("mod")
                .setRoles(List.of(moderatorRole));

        userRepository.save(moderatorUser);
    }

    private void initNormalUser(){
        UserEntity moderatorUser = new UserEntity()
                .setUsername("user")
                .setPassword(passwordEncoder.encode("topsecret"))
                .setEmail("user@example.com")
                .setFirstName("user")
                .setLastName("user");

        userRepository.save(moderatorUser);
    }
}
