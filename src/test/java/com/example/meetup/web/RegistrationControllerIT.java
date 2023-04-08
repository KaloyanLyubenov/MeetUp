package com.example.meetup.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testRegistration() throws Exception {
        byte[] emptyBytes = new byte[0];
        MockMultipartFile emptyFile = new MockMultipartFile("empty.txt", "empty.txt", "text/plain", emptyBytes);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/users/register")
                        .file(emptyFile)
                        .param("firstName", "test")
                .param("lastName", "testov")
                .param("username", "testMan")
                .param("email", "test@est.com")
                .param("password", "topsecret")
                .param("confirmPassword", "topsecret")
                .with(csrf())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

}
