package com.lv.conf.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ConferenceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnConferenceIdWhenCreateConference() {
        try {
            String json = getResource("fixtures/conference.json");

            mockMvc.perform(post("/api/v1/conferences")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("Location", "/api/v1/conferences/1"));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getResource(String resourceName) throws IOException {
        return new String(getClass()
                .getClassLoader()
                .getResourceAsStream(resourceName)
                .readAllBytes());
    }
}