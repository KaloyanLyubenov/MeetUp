package com.example.meetup.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.example.meetup.domain.customExceptions.ObjectNotFoundException;
import com.example.meetup.domain.dto.binding.UserEditDTO;
import com.example.meetup.domain.dto.models.UserModel;
import com.example.meetup.domain.dto.views.UserIndexView;
import com.example.meetup.domain.entities.PictureEntity;
import com.example.meetup.domain.entities.UserEntity;
import com.example.meetup.domain.entities.UserRoleEntity;
import com.example.meetup.domain.enums.UserRoleEnum;
import com.example.meetup.repository.PictureRepository;
import com.example.meetup.repository.UserRepository;
import com.example.meetup.repository.UserRoleRepository;
import com.example.meetup.service.ImageCloudService;
import com.example.meetup.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private final Long EXISTING_USER_ID = (long)1;
    private final Long NOT_EXISTING_USER_ID = (long)2;
    private final String EXISTING_USER_USERNAME = "test";
    private final String NOT_EXISTING_USER_USERNAME = "nottest";

    private UserService toTest;

    @Mock
    private  UserRepository mockUserRepository;
    @Mock
    private  PasswordEncoder mockPasswordEncoder;
    @Mock
    private  UserDetailsService mockUserDetailsService;
    @Mock
    private  ModelMapper mockModelMapper;
    @Mock
    private  ImageCloudService mockImageCloudService;
    @Mock
    private  UserRoleRepository mockUserRoleRepository;
    @Mock
    private  PictureRepository mockPictureRepository;

    @BeforeEach
    void setUp(){
        toTest = new UserService(mockUserRepository,
                mockPasswordEncoder,
                mockUserDetailsService,
                mockModelMapper,
                mockImageCloudService,
                mockUserRoleRepository,
                mockPictureRepository);
    }

    @Test
    void testUserFoundByUsername() {
        //Arrange
        UserEntity testUserEntity = new UserEntity()
                .setUsername(EXISTING_USER_USERNAME)
                .setPassword("topsecret")
                .setEmail("test@test.com")
                .setFirstName("test")
                .setLastName("testov");
        testUserEntity.setId(EXISTING_USER_ID);

        UserModel testUserModel = new UserModel()
                .setId(EXISTING_USER_ID)
                .setUsername(testUserEntity.getUsername())
                .setPassword(testUserEntity.getPassword())
                .setEmail(testUserEntity.getEmail())
                .setFirstName(testUserEntity.getFirstName())
                .setLastName(testUserEntity.getLastName());

        when(mockUserRepository.findUserEntityByUsername(EXISTING_USER_USERNAME))
                .thenReturn(Optional.of(testUserEntity));

        when(mockModelMapper.map(mockUserRepository.findUserEntityByUsername(EXISTING_USER_USERNAME), UserModel.class))
                .thenReturn(testUserModel);

        //Act

        UserModel testResult = toTest.getUserByUsername(EXISTING_USER_USERNAME);

        //Assert

        Assertions.assertNotNull(testResult);
        Assertions.assertEquals(EXISTING_USER_ID, testResult.getId());
        Assertions.assertEquals(EXISTING_USER_USERNAME, testResult.getUsername());
        Assertions.assertEquals("test", testResult.getFirstName());
        Assertions.assertEquals("testov", testResult.getLastName());
        Assertions.assertEquals("topsecret", testResult.getPassword());
        Assertions.assertEquals("test@test.com", testResult.getEmail());
    }

    @Test
    void estUserNotFoundByUsername(){
        assertThrows(
                ObjectNotFoundException.class,
                () -> toTest.getUserByUsername(NOT_EXISTING_USER_USERNAME)
        );
    }

    @Test
    void testUserFoundById() {
        //Arrange
        UserEntity testUserEntity = new UserEntity()
                .setUsername(EXISTING_USER_USERNAME)
                .setPassword("topsecret")
                .setEmail("test@test.com")
                .setFirstName("test")
                .setLastName("testov");
        testUserEntity.setId(EXISTING_USER_ID);

        UserModel testUserModel = new UserModel()
                .setId(EXISTING_USER_ID)
                .setUsername(testUserEntity.getUsername())
                .setPassword(testUserEntity.getPassword())
                .setEmail(testUserEntity.getEmail())
                .setFirstName(testUserEntity.getFirstName())
                .setLastName(testUserEntity.getLastName());

        when(mockUserRepository.findById(EXISTING_USER_ID))
                .thenReturn(Optional.of(testUserEntity));

        when(mockModelMapper.map(mockUserRepository.findById(EXISTING_USER_ID), UserModel.class))
                .thenReturn(testUserModel);

        //Act

        UserModel testResult = toTest.getUserById(EXISTING_USER_ID);

        //Assert

        Assertions.assertNotNull(testResult);
        Assertions.assertEquals(EXISTING_USER_ID, testResult.getId());
        Assertions.assertEquals(EXISTING_USER_USERNAME, testResult.getUsername());
        Assertions.assertEquals("test", testResult.getFirstName());
        Assertions.assertEquals("testov", testResult.getLastName());
        Assertions.assertEquals("topsecret", testResult.getPassword());
        Assertions.assertEquals("test@test.com", testResult.getEmail());
    }

    @Test
    void testUserNotFoundById(){
        assertThrows(
                ObjectNotFoundException.class,
                () -> toTest.getUserById(NOT_EXISTING_USER_ID)
        );
    }

    @Test
    void testAllUserIndexViewsFound(){
        //Arrange
        UserEntity testUserEntity = new UserEntity()
                .setUsername(EXISTING_USER_USERNAME)
                .setFirstName("test")
                .setLastName("testov")
                .setRoles(List.of(new UserRoleEntity().setUserRole(UserRoleEnum.ADMIN),
                                new UserRoleEntity().setUserRole(UserRoleEnum.MODERATOR)))
                .setProfilePicture(new PictureEntity().setUrl("url"));


        testUserEntity.setId(EXISTING_USER_ID);

        when(mockUserRepository.findAll())
                .thenReturn(List.of(testUserEntity));

        //Act
        List<UserIndexView> testResult = toTest.getAllUsersIndexView();

        //Assert
        Assertions.assertNotNull(testResult);
        Assertions.assertEquals(EXISTING_USER_ID, testResult.get(0).getId());
        Assertions.assertEquals(EXISTING_USER_USERNAME, testResult.get(0).getUsername());
        Assertions.assertEquals("test", testResult.get(0).getFirstName());
        Assertions.assertEquals("testov", testResult.get(0).getLastName());
        Assertions.assertTrue(testResult.get(0).getIsAdmin());
        Assertions.assertTrue(testResult.get(0).getIsModerator());
        Assertions.assertEquals("url", testResult.get(0).getProfPicUrl());

    }

    @Test
    void testUserToEditFoundById() {
        UserEntity testUserEntity = new UserEntity()
                .setUsername(EXISTING_USER_USERNAME)
                .setEmail("test@test.com")
                .setFirstName("test")
                .setLastName("testov");
        testUserEntity.setId(EXISTING_USER_ID);

        UserEditDTO testUserEditDTO = new UserEditDTO()
                .setId(EXISTING_USER_ID)
                .setUsername(testUserEntity.getUsername())
                .setEmail(testUserEntity.getEmail())
                .setFirstName(testUserEntity.getFirstName())
                .setLastName(testUserEntity.getLastName());

        when(mockUserRepository.findById(EXISTING_USER_ID))
                .thenReturn(Optional.of(testUserEntity));

        when(mockModelMapper.map(mockUserRepository.findById(EXISTING_USER_ID), UserEditDTO.class))
                .thenReturn(testUserEditDTO);

        //Act

        UserEditDTO testResult = toTest.getUserToEdit(EXISTING_USER_ID);

        //Assert

        Assertions.assertNotNull(testResult);
        Assertions.assertEquals(EXISTING_USER_ID, testResult.getId());
        Assertions.assertEquals(EXISTING_USER_USERNAME, testResult.getUsername());
        Assertions.assertEquals("test", testResult.getFirstName());
        Assertions.assertEquals("testov", testResult.getLastName());
        Assertions.assertEquals("test@test.com", testResult.getEmail());
    }

}
