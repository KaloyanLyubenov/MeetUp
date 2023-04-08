package com.example.meetup.web;

import com.example.meetup.domain.dto.views.UserIndexView;
import com.example.meetup.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerIT {

    @Mock
    private UserService mockUserService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "test", roles = {"ADMIN"})
    public void testGetAdmins() throws Exception {
        UserIndexView testUserIndexView = new UserIndexView()
                .setId((long)1)
                .setUsername("testMan")
                .setFirstName("test")
                .setLastName("test")
                .setAdmin(true)
                .setModerator(true);

        List<UserIndexView> testUsers = List.of(testUserIndexView);

        when(mockUserService.getAllUsersIndexView())
                .thenReturn(testUsers);

        mockMvc.perform(get("/admins"))
                .andExpect(status().isOk());
    }
}
