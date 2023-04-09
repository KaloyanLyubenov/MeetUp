package com.example.meetup.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.example.meetup.domain.customExceptions.ObjectNotFoundException;
import com.example.meetup.domain.dto.binding.EditMeetDTO;
import com.example.meetup.domain.dto.models.*;
import com.example.meetup.domain.dto.views.MeetDetailsView;
import com.example.meetup.domain.dto.views.MeetIndexView;
import com.example.meetup.domain.dto.views.PopularMeetView;
import com.example.meetup.domain.entities.*;
import com.example.meetup.domain.enums.MeetTypeEnum;
import com.example.meetup.domain.enums.VehicleTypeEnum;
import com.example.meetup.repository.CommentRepository;
import com.example.meetup.repository.MeetRepository;
import com.example.meetup.repository.PictureRepository;
import com.example.meetup.repository.UserRepository;
import com.example.meetup.service.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class MeetServiceTest {

    private final Long EXISTING_MEET_ID = (long)1;
    private final Long EXISTING_ANNOUNCER_ID = (long)1;
    private final Long NOT_EXISTING_MEET_ID = (long)2;

    private MeetService toTest;

    @Mock
    private UserService mockUserService;
    @Mock
    private MeetTypeService mockMeetTypeService;
    @Mock
    private MeetRepository mockMeetRepository;
    @Mock
    private VehicleTypeService mockVehicleTypeService;
    @Mock
    private ModelMapper mockModelMapper;
    @Mock
    private ImageCloudService mockImageCloudService;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private CommentRepository mockCommentRepository;
    @Mock
    private PictureRepository mockPictureRepository;

    @BeforeEach
    void setUp() {
        toTest = new MeetService(mockUserService,
                mockMeetTypeService,
                mockMeetRepository,
                mockVehicleTypeService,
                mockModelMapper,
                mockImageCloudService,
                mockUserRepository,
                mockCommentRepository,
                mockPictureRepository);
    }

    @Test
    void testAllMeetsFound() {
        //Arrange
        MeetEntity testMeetEntity = new MeetEntity()
                .setMeetTitle("meetTitle")
                .setDescription("description")
                .setThumbnail(new PictureEntity().setUrl("url"))
                .setDate(new Date());

        testMeetEntity.setId(EXISTING_MEET_ID);


        when(mockMeetRepository.findAll())
                .thenReturn(List.of(testMeetEntity));

        //Act
        List<MeetIndexView> testResult = toTest.getAllMeets();

        //Assert
        Assertions.assertNotNull(testResult);
        Assertions.assertEquals(testMeetEntity.getMeetTitle(), testResult.get(0).getMeetTitle());
        Assertions.assertEquals(testMeetEntity.getDescription(), testResult.get(0).getDescription());
    }

    @Test
    void testMeetDetailsFoundById(){
        //Arrange
        UserEntity testAnnouncerEntity = new UserEntity().setFirstName("ivan").setLastName("ivanov");
        testAnnouncerEntity.setId((long)1);

        UserEntity testAnnouncerModel = new UserEntity().setFirstName("ivan").setLastName("ivanov");
        testAnnouncerModel.setId((long)1);

        MeetEntity testMeetEntity = new MeetEntity()
                .setMeetTitle("meetTitle")
                .setMeetType(new MeetTypeEntity())
                .setVehicleType(new VehicleTypeEntity())
                .setDescription("description")
                .setThumbnail(new PictureEntity().setUrl("url"))
                .setDate(new Date())
                .setAnnouncer(testAnnouncerEntity)
                .setPictures(List.of(new PictureEntity()))
                .setParticipants(List.of(testAnnouncerEntity));

        testMeetEntity.setId(EXISTING_MEET_ID);



        when(mockMeetRepository.findById(EXISTING_MEET_ID))
                .thenReturn(Optional.of(testMeetEntity));

        when(mockModelMapper.map(mockMeetRepository.findById(EXISTING_MEET_ID), MeetEntity.class))
                .thenReturn(testMeetEntity);

        //Act
        MeetDetailsView testResult = toTest.getMeetDetails(EXISTING_MEET_ID);

        //Assert

        Assertions.assertNotNull(testResult);
        Assertions.assertEquals(EXISTING_MEET_ID, testResult.getId());
        Assertions.assertEquals(testMeetEntity.getMeetTitle(), testResult.getMeetTitle());
        Assertions.assertEquals(testMeetEntity.getDescription(), testResult.getDescription());
        Assertions.assertEquals(testMeetEntity.getThumbnail().getUrl(), testResult.getThumbnailUrl());
    }

    @Test
    void testMeetDetailsNotFoundById() {
        assertThrows(
                ObjectNotFoundException.class,
                () -> toTest.getMeetDetails(NOT_EXISTING_MEET_ID)
        );
    }

    @Test
    void testPopularMeetsFound() {
        //Arrange
        MeetEntity testMeetEntity = new MeetEntity()
                .setMeetTitle("meetTitle")
                .setMeetType(new MeetTypeEntity())
                .setThumbnail(new PictureEntity().setUrl("url"));

        testMeetEntity.setId(EXISTING_MEET_ID);

        when(mockMeetRepository.findFirst4ByOrderByIdAsc())
                .thenReturn(List.of(testMeetEntity));

        //Act
        List<PopularMeetView> testResult = toTest.getPopularMeets();

        //Assert
        Assertions.assertNotNull(testResult);
        Assertions.assertEquals(testMeetEntity.getMeetTitle(), testResult.get(0).getMeetTitle());
    }

    @Test
    void testMeetToEditFoundById() {
        //Arrange
        MeetModel testMeetEntity = new MeetModel()
                .setMeetTitle("meetTitle")
                .setMeetType(new MeetTypeModel().setMeetType(MeetTypeEnum.RoadTrip))
                .setVehicleType(new VehicleTypeModel().setVehicleType(VehicleTypeEnum.Car))
                .setDescription("description")
                .setThumbnail(new PictureModel().setUrl("url"))
                .setDate(new Date())
                .setAnnouncer(new UserModel())
                .setPictures(List.of(new PictureModel()))
                .setParticipants(List.of(new UserModel()));

        testMeetEntity.setId(EXISTING_MEET_ID);

        when(mockModelMapper.map(mockMeetRepository.findById(EXISTING_MEET_ID), MeetModel.class))
                .thenReturn(testMeetEntity);

        //Act

        EditMeetDTO testResult = toTest.findMeetToEditById(EXISTING_MEET_ID);

        //Assert

        Assertions.assertNotNull(testResult);
        Assertions.assertEquals(EXISTING_MEET_ID, testResult.getId());
        Assertions.assertEquals(testMeetEntity.getMeetTitle(), testResult.getMeetTitle());
        Assertions.assertEquals("RoadTrip", testResult.getMeetType());
        Assertions.assertEquals("Car", testResult.getVehicleType());
        Assertions.assertEquals(testMeetEntity.getDescription(), testResult.getDescription());
    }

    @Test
    void testMeetToEditNotFoundById() {
        assertThrows(
                ObjectNotFoundException.class,
                () -> toTest.findMeetToEditById(NOT_EXISTING_MEET_ID)
        );
    }

    @Test
    void testMeetFoundByAnnouncerId() {
        //Arrange
        MeetEntity testMeetEntity = new MeetEntity()
                .setMeetTitle("meetTitle")
                .setDescription("description")
                .setThumbnail(new PictureEntity().setUrl("url"))
                .setDate(new Date());

        testMeetEntity.setId(EXISTING_MEET_ID);

        when(mockMeetRepository.findAllByAnnouncer_Id(EXISTING_ANNOUNCER_ID))
                .thenReturn(List.of(testMeetEntity));

        //Act
        List<MeetIndexView> testResult = toTest.getMeetsIndexViewByAnnouncerId(EXISTING_ANNOUNCER_ID);

        //Assert

        Assertions.assertNotNull(testResult);
        Assertions.assertEquals(testMeetEntity.getId(), testResult.get(0).getId());
        Assertions.assertEquals(testMeetEntity.getMeetTitle(), testResult.get(0).getMeetTitle());
        Assertions.assertEquals(testMeetEntity.getDescription(), testResult.get(0).getDescription());
        Assertions.assertEquals(testMeetEntity.getThumbnail().getUrl(), testResult.get(0).getThumbnailUrl());
    }

    @Test
    void testMeetNotFoundByAnnouncerId(){
        assertThrows(
                ObjectNotFoundException.class,
                () -> toTest.getMeetsIndexViewByAnnouncerId(NOT_EXISTING_MEET_ID)
        );
    }
}