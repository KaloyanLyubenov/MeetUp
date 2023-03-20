package com.example.meetup.service;

import com.example.meetup.domain.dto.UserModel;
import com.example.meetup.domain.dto.binding.UserRegisterDTO;
import com.example.meetup.domain.entities.PictureEntity;
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
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final ModelMapper modelMapper;
    private final ImageCloudService imageCloudService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService, ModelMapper modelMapper, ImageCloudService imageCloudService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.modelMapper = modelMapper;
        this.imageCloudService = imageCloudService;
    }

    public void registerUser(UserRegisterDTO userRegisterDTO,
                             Consumer<Authentication> successfulLoginProcessor){

        PictureEntity profPic = new PictureEntity()
                .setUrl(imageCloudService.saveImage(userRegisterDTO.getProfilePicture()));

        UserEntity user = new UserEntity()
                .setUsername(userRegisterDTO.getUsername())
                .setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()))
                .setEmail(userRegisterDTO.getEmail())
                .setFirstName(userRegisterDTO.getFirstName())
                .setLastName(userRegisterDTO.getLastName())
                .setProfilePicture(profPic);

        userRepository.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(userRegisterDTO.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );

        successfulLoginProcessor.accept(authentication);

    }

    public UserModel getUserByUsername(String username){
        return this.modelMapper.map(this.userRepository.findUserEntityByUsername(username).orElse(new UserEntity()), UserModel.class);
    }

}
