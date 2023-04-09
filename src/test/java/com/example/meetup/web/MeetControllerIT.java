package com.example.meetup.web;

import com.example.meetup.service.MeetService;
import com.example.meetup.service.PictureService;
import com.example.meetup.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class MeetControllerIT {

    private MeetController topTest;

    @Mock
    private MeetService mockMeetService;
    @Mock
    private PictureService mockPictureService;
    @Mock
    private UserService mockUserService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        topTest = new MeetController(
                mockMeetService,
                mockPictureService,
                mockUserService
        );
    }

    @Test
    @WithMockUser(username = "test", roles = {"ADMIN"})
    void testAddMeet() throws Exception {
        mockMvc.perform(post("/meets/add")
                .param("meetTitle","title")
                .param("meetType","RoadTrip")
                .param("vehicleType","Car")
                .param("description", "description")
                .with(csrf())
        )
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", roles = {"ADMIN"})
    void testPostEditMeet() throws Exception {
        mockMvc.perform(post("/meets/edit/1")
                        .param("id","1")
                        .param("meetTitle","title")
                        .param("meetType","RoadTrip")
                        .param("vehicleType","Car")
                        .param("description", "description")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}


