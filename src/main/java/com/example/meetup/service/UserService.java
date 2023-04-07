package com.example.meetup.service;

import com.example.meetup.domain.customExceptions.ObjectNotFoundException;
import com.example.meetup.domain.dto.models.UserModel;
import com.example.meetup.domain.dto.binding.UserEditDTO;
import com.example.meetup.domain.dto.binding.UserRegisterDTO;
import com.example.meetup.domain.dto.views.UserIndexView;
import com.example.meetup.domain.entities.PictureEntity;
import com.example.meetup.domain.entities.UserEntity;
import com.example.meetup.domain.entities.UserRoleEntity;
import com.example.meetup.domain.enums.UserRoleEnum;
import com.example.meetup.repository.PictureRepository;
import com.example.meetup.repository.UserRepository;
import com.example.meetup.repository.UserRoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

//ErrorHandling implemented


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final ModelMapper modelMapper;
    private final ImageCloudService imageCloudService;
    private final UserRoleRepository userRoleRepository;
    private final PictureRepository pictureRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService, ModelMapper modelMapper, ImageCloudService imageCloudService, UserRoleRepository userRoleRepository, PictureRepository pictureRepository) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.modelMapper = modelMapper;
        this.imageCloudService = imageCloudService;
        this.userRoleRepository = userRoleRepository;
        this.pictureRepository = pictureRepository;
    }

    public void registerUser(UserRegisterDTO userRegisterDTO,
                             Consumer<Authentication> successfulLoginProcessor){

        PictureEntity profPic = new PictureEntity()
                .setUrl(imageCloudService.saveImage(userRegisterDTO.getProfilePicture()));

        this.pictureRepository.save(profPic);

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
        UserModel user = this.modelMapper.map(this.userRepository.findUserEntityByUsername(username), UserModel.class);

        if(user == null){
            throw new ObjectNotFoundException(username, "Username", "User");
        }

        return user;
    }

    public UserModel getUserById(Long id){
        UserModel userToReturn = this.modelMapper.map(this.userRepository.findById(id), UserModel.class);

        if(userToReturn == null){
            throw new ObjectNotFoundException(id.toString(), "ID", "User");
        }

        return userToReturn;
    }

    public UserModel getUserByMeetId(Long meetId){
        UserModel user = this.modelMapper.map(this.userRepository.findByMeetId(meetId), UserModel.class);

        if(user == null){
            throw new ObjectNotFoundException(meetId.toString(), "MeetID", "User");
        }

        return user;
    }

    public List<UserIndexView> getAllUsersIndexView(){
        return this.userRepository.findAll()
                .stream().map(user -> new UserIndexView()
                        .setId(user.getId())
                        .setUsername(user.getUsername())
                        .setFirstName(user.getFirstName())
                        .setLastName(user.getLastName())
                        .setAdmin(user.isAdmin())
                        .setModerator(user.isModerator())
                        .setProfPicUrl(user.getProfilePicture().getUrl()))
                .collect(Collectors.toList());
    }

    public void makeAdmin(Long userId){
        UserEntity user = this.modelMapper.map(this.userRepository.findById(userId), UserEntity.class);


        if(user == null){
            throw new ObjectNotFoundException(userId.toString(), "ID", "User");
        }

        UserRoleEntity adminEntity = this.modelMapper.map(this.userRoleRepository.findByUserRole(UserRoleEnum.ADMIN), UserRoleEntity.class);

        user.getRoles().add(adminEntity);

        this.userRepository.saveAndFlush(user);
    }

    public void removeAdmin(Long userId){
        UserEntity user = this.modelMapper.map(this.userRepository.findById(userId), UserEntity.class);

        if(user == null){
            throw new ObjectNotFoundException(userId.toString(), "ID", "User");
        }

        UserRoleEntity adminEntity = this.userRoleRepository.findByUserRole(UserRoleEnum.ADMIN).get();

        user.getRoles().remove(adminEntity);

        this.userRepository.saveAndFlush(user);
    }

    public void makeModerator(Long userId){
        UserEntity user = this.modelMapper.map(this.userRepository.findById(userId), UserEntity.class);

        if(user == null){
            throw new ObjectNotFoundException(userId.toString(), "ID", "User");
        }

        UserRoleEntity moderatorEntity = this.userRoleRepository.findByUserRole(UserRoleEnum.MODERATOR).get();

        user.getRoles().add(moderatorEntity);

        this.userRepository.saveAndFlush(user);
    }

    public void removeModerator(Long userId){
        UserEntity user = this.modelMapper.map(this.userRepository.findById(userId), UserEntity.class);

        if(user == null){
            throw new ObjectNotFoundException(userId.toString(), "ID", "User");
        }

        UserRoleEntity moderatorEntity = this.userRoleRepository.findByUserRole(UserRoleEnum.MODERATOR).get();

        user.getRoles().remove(moderatorEntity);

        this.userRepository.saveAndFlush(user);
    }

    public UserEditDTO getUserToEdit(Long id){
        UserEditDTO user = this.modelMapper.map(this.userRepository.findById(id), UserEditDTO.class);

        if(user == null){
            throw new ObjectNotFoundException(id.toString(), "ID", "User");
        }

        return user;
    }

    public void saveEditedUser(UserEditDTO editedUser){
        UserEntity userToSave = this.modelMapper.map(this.userRepository.findById(editedUser.getId()), UserEntity.class);

        userToSave.setUsername(editedUser.getUsername())
                .setFirstName(editedUser.getFirstName())
                .setLastName(editedUser.getLastName())
                .setEmail(editedUser.getEmail());

        this.userRepository.saveAndFlush(userToSave);
    }
}
