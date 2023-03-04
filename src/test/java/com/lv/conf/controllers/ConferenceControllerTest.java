package com.lv.conf.controllers;

import com.lv.conf.models.Conference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static groovy.json.JsonOutput.toJson;
import static org.mockito.ArgumentMatchers.any;
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
            mockMvc.perform(post("/api/v1/conferences")
                            .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(Conference
                                    .builder()
                                    .id(1L)
                                    .name("Spring Boot Conf")
                                    .timeTable(any())
                                    .roomId(55L)
                                    .build())))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("Location", "/api/v1/conferences/1"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}