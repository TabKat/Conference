package com.lv.conf.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
                            .content("{\"name\":\"SpringBoot2023\",\"timeTable\":[{\"conferenceId\":\"123-456\",\"startDate\":\"2023-05-01 10:00\",\"endDate\":\"2023-05-01 18:00\"},{\"conferenceId\":\"123-456\",\"startDate\":\"2023-05-02 10:00\",\"endDate\":\"2023-05-01 17:00\"}],\"roomId\":55}"))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("Location", "/api/v1/conferences/1"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}