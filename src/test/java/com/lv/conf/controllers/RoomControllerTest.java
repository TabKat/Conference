package com.lv.conf.controllers;

import com.lv.conf.models.Room;
import com.lv.conf.services.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static groovy.json.JsonOutput.toJson;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RoomController.class)
class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomService roomService;

    private Room room;

    @BeforeEach
    void setup() {
        room = Room
            .builder()
            .id(1L)
            .name("North")
            .totalSits(50)
            .build();
    }

    @Test
    void shouldReturnRoom() throws Exception {
        when(roomService.getRoom(1L)).thenReturn(Optional.of(room));

        mockMvc.perform(get("/api/v1/rooms/1")
            .contentType(APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.name", is("North")))
            .andExpect(jsonPath("$.totalSits", is(50)));
    }

    @Test
    void shouldCreateRoom() throws Exception {
        when(roomService.addRoom(room)).thenReturn(1L);

        mockMvc.perform(post("/api/v1/rooms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(room)))
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", "/api/v1/rooms/1"));
    }

    @Test
    void shouldRemoveRoom() throws Exception {
        roomService.addRoom(room);
        mockMvc.perform(delete("/api/v1/rooms/1"))
             .andExpect(status().is2xxSuccessful());
    }
}
