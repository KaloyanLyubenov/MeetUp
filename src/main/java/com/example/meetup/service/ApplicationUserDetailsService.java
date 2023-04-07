package com.example.meetup.service;

import com.example.meetup.domain.AppUserDetails;
import com.example.meetup.domain.entities.UserEntity;
import com.example.meetup.domain.entities.UserRoleEntity;
import com.example.meetup.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class ApplicationUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public ApplicationUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = userRepository.
                findUserEntityByUsername(username).
                map(this::map).
                orElseThrow(() -> new UsernameNotFoundException("Username with name " + username + " was not found!"));

        return userDetails;
    }

    private UserDetails map(UserEntity userEntity){
        return new AppUserDetails(
                userEntity.getUsername(),
                userEntity.getPassword(),
                extractAuthorities(userEntity)
        ).setEntityId(userEntity.getId());
    }

    private List<GrantedAuthority> extractAuthorities(UserEntity userEntity){
        return userEntity.
                getRoles().
                stream().
                map(this::mapRole).
                toList();
    }

    private GrantedAuthority mapRole(UserRoleEntity userRole){
        return new SimpleGrantedAuthority(("ROLE_" + userRole.getUserRole().name()));
    }


}
