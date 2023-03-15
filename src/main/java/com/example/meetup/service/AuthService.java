package com.example.meetup.service;

import com.example.meetup.domain.dto.UserRegisterModel;
import com.example.meetup.domain.entities.UserEntity;
import com.example.meetup.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.userRepository = userRepository;

        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    public void registerUser(UserRegisterModel userRegisterModel,
                             Consumer<Authentication> successfulLoginProcessor){

        UserEntity user = new UserEntity()
                .setUsername(userRegisterModel.getUsername())
                .setPassword(passwordEncoder.encode(userRegisterModel.getPassword()))
                .setEmail(userRegisterModel.getEmail())
                .setFirstName(userRegisterModel.getFirstName())
                .setLastName(userRegisterModel.getLastName());

        userRepository.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(userRegisterModel.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );

        successfulLoginProcessor.accept(authentication);

    }

}
