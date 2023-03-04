package com.lv.conf.controllers;

import com.lv.conf.models.Conference;
import com.lv.conf.models.TimeTable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static groovy.json.JsonOutput.toJson;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ConferenceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnConferenceIdWhenCreateConference() {
        try {
            List<TimeTable> timeTables = new ArrayList<>();
            timeTables.add(TimeTable
                    .builder()
                    .conferenceId("123-456")
                    .startDate(LocalDateTime.of(2023, 05, 01, 10,00))
                    .endDate(LocalDateTime.of(2023, 05, 01, 18,00))
                    .build());

            mockMvc.perform(post("/api/v1/conferences")
                            .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(Conference
                                    .builder()
                                    .id(1L)
                                    .name("Spring Boot 2023")
                                    .timeTable(timeTables) // TODO: fix bug with json formatting "startDate":{"dayOfMonth":1,"dayOfWeek":"MONDAY"...
                                    .roomId(55L)
                                    .build())))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("Location", "/api/v1/conferences/1"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}