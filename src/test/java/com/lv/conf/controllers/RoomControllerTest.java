package com.lv.conf.controllers;

import com.lv.conf.models.Room;
import com.lv.conf.services.RoomService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static groovy.json.JsonOutput.toJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoomService roomService;

    @Test
    void shouldReturnRoomIdWhenRoomCreated() {
        try {
           mockMvc.perform(post("/api/v1/rooms")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(Room
                            .builder()
                            .id(1L)
                            .name("North")
                            .totalSits(200L)
                            .build())
                    ))
                   .andExpect(status().isCreated())
                   .andExpect(header().string("Location", "/api/v1/rooms/1"));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
