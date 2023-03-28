package com.lv.conf.controllers;

import com.lv.conf.models.Conference;
import com.lv.conf.models.ConferenceDto;
import com.lv.conf.models.Sit;
import com.lv.conf.models.TimeTable;
import com.lv.conf.repositories.TimeTableRepository;
import com.lv.conf.services.ConferenceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static groovy.json.JsonOutput.toJson;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ConferenceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TimeTableRepository timeTableRepository;

    @MockBean
    private ConferenceService conferenceService;

    private TimeTable timeTable;

    @BeforeEach
    void init() {
        timeTable = TimeTable
            .builder()
            .conferenceId("123-456")
            .startDate(LocalDateTime.of(2023, Month.MAY, 5, 2, 10, 0))
            .endDate(LocalDateTime.of(2023, Month.MAY, 5, 2, 18, 0))
            .build();
    }

    @Test
    void shouldReturnConferenceIdWhenCreateConference() throws Exception {
        timeTableRepository.save(timeTable);

        var conference = Conference
            .builder()
            .id(1L)
            .name("Spring Boot 2023")
            .roomId(55L)
            .build();

        when(conferenceService.addConference(conference)).thenReturn(1L);

        mockMvc.perform(post("/api/v1/conferences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(conference)))
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", "/api/v1/conferences/1"));
    }

    @Test
    void shouldReturnConference() throws Exception {
        List<Sit> sits = new ArrayList<>();
        sits.add(Sit
            .builder()
            .id(1L)
            .roomId(1L)
            .reservedSit(1L)
            .conferenceId(1L)
            .build());
        var conference = getConference();

        when(conferenceService.getConference(1L)).thenReturn(ConferenceDto
             .builder()
             .conference(conference)
             .sits(sits)
             .build());

        mockMvc.perform(get("/api/v1/conferences/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.conference.id", is(1)))
            .andExpect(jsonPath("$.conference.name", is("Spring Boot 2023")))
            .andExpect(jsonPath("$.conference.roomId", is(55)))
            .andExpect(jsonPath("$.conference.timeTable[0].conferenceId", is("123-456")))
            .andExpect(jsonPath("$.conference.timeTable[0].startDate", is("2023-05-05 02:10")))
            .andExpect(jsonPath("$.conference.timeTable[0].endDate", is("2023-05-05 02:18")))
            .andExpect(jsonPath("$.sits[0].id", is(1)))
            .andExpect(jsonPath("$.sits[0].reservedSit", is(1)))
            .andExpect(jsonPath("$.sits[0].conferenceId", is(1)))
            .andExpect(jsonPath("$.sits[0].roomId", is(1)));
    }

    @Test
    void shouldRemoveRoom() throws Exception {
        conferenceService.addConference(getConference());
        mockMvc.perform(delete("/api/v1/conferences/1"))
                .andExpect(status().is2xxSuccessful());
    }

    private Conference getConference() {
        List<TimeTable> tm = new ArrayList<>();
        tm.add(timeTable);

        return Conference
                .builder()
                .id(1L)
                .name("Spring Boot 2023")
                .roomId(55L)
                .timeTable(tm)
                .build();
    }
}