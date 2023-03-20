package com.lv.conf.controllers;

import com.lv.conf.models.Conference;
import com.lv.conf.models.TimeTable;
import com.lv.conf.repositories.TimeTableRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;

import static groovy.json.JsonOutput.toJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ConferenceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TimeTableRepository timeTableRepository;

    @Test
    void shouldReturnConferenceIdWhenCreateConference() {
        try {

            TimeTable tmTable = TimeTable
                    .builder()
                            .conferenceId("123-456")
                            .startDate(LocalDateTime.of(2023, Month.MAY, 5, 2, 10, 0))
                            .endDate(LocalDateTime.of(2023, Month.MAY, 5, 2, 18, 0))
                    .build();

            timeTableRepository.save(tmTable);

            mockMvc.perform(post("/api/v1/conferences")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(Conference
                                    .builder()
                                    .name("Spring Boot 2023")
                                    .roomId(55L)
                                    .build())))
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