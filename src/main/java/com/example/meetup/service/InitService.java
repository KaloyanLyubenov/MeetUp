package com.example.meetup.service;

import com.example.meetup.domain.dto.models.MeetTypeModel;
import com.example.meetup.domain.dto.models.UserRoleModel;
import com.example.meetup.domain.dto.models.VehicleTypeModel;
import com.example.meetup.domain.entities.*;
import com.example.meetup.domain.enums.MeetTypeEnum;
import com.example.meetup.domain.enums.UserRoleEnum;
import com.example.meetup.domain.enums.VehicleTypeEnum;
import com.example.meetup.repository.*;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class InitService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final VehicleTypeRepository vehicleTypeRepository;
    private final MeetTypeRepository meetTypeRepository;
    private final ModelMapper modelMapper;
    private final PictureRepository pictureRepository;

    @Autowired
    public InitService(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder, VehicleTypeRepository vehicleTypeRepository, MeetTypeRepository meetTypeRepository, ModelMapper modelMapper, PictureRepository pictureRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.vehicleTypeRepository = vehicleTypeRepository;
        this.meetTypeRepository = meetTypeRepository;
        this.modelMapper = modelMapper;
        this.pictureRepository = pictureRepository;
    }

    @PostConstruct
    public void init(){
        initDefaultPicture();
        initRoles();
        initUsers();
        initMeetTypes();
        initVehicleTypes();
    }

    private void initRoles() {
        if(this.userRoleRepository.count() == 0){
            this.userRoleRepository.saveAllAndFlush((Arrays.stream(UserRoleEnum.values()))
                    .map(enumValue -> new UserRoleModel().setRole(enumValue))
                    .map(userRoleModel -> this.modelMapper.map(userRoleModel, UserRoleEntity.class))
                    .toList());
        }
    }

    private void initVehicleTypes(){
        if(this.vehicleTypeRepository.count() == 0){
            this.vehicleTypeRepository.saveAllAndFlush((Arrays.stream(VehicleTypeEnum.values()))
                    .map(enumValue -> new VehicleTypeModel().setVehicleType(enumValue))
                    .map(vehicleTypeModel -> this.modelMapper.map(vehicleTypeModel, VehicleTypeEntity.class))
                    .toList());
        }
    }

    private void initMeetTypes(){
        if(this.meetTypeRepository.count() == 0){
            this.meetTypeRepository.saveAllAndFlush((Arrays.stream(MeetTypeEnum.values()))
                    .map(enumValue -> new MeetTypeModel().setMeetType(enumValue))
                    .map(meetTypeModel -> this.modelMapper.map(meetTypeModel, MeetTypeEntity.class))
                    .toList());
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
                .setRoles(userRoleRepository.findAll())
                .setProfilePicture(this.modelMapper.map(this.pictureRepository.findById((long)1), PictureEntity.class));

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
                .setRoles(List.of(moderatorRole))
                .setProfilePicture(this.modelMapper.map(this.pictureRepository.findById((long)1), PictureEntity.class));

        userRepository.save(moderatorUser);
    }

    private void initNormalUser(){
        UserEntity moderatorUser = new UserEntity()
                .setUsername("user")
                .setPassword(passwordEncoder.encode("topsecret"))
                .setEmail("user@example.com")
                .setFirstName("user")
                .setLastName("user")
                .setProfilePicture(this.modelMapper.map(this.pictureRepository.findById((long)1), PictureEntity.class));

        userRepository.save(moderatorUser);
    }

    private void initDefaultPicture(){
        if(this.pictureRepository.count() == 0) {
            PictureEntity picture = new PictureEntity()
                    .setUrl("https://res.cloudinary.com/ddwhij8d7/image/upload/v1679322618/icon-256x256_fpo48i.png");

            this.pictureRepository.save(picture);
        }
    }
}