package com.lv.conf.controllers;

import com.lv.conf.models.Room;
import com.lv.conf.services.RoomService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = RoomController.class)
@AutoConfigureMockMvc
class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoomService roomService;

    @Test
    void shouldReturnRoomIdWhenRoomCreated() {
        try {
            this.mockMvc.perform(post("/api/v1/room")
                    .content(String.valueOf(Room
                            .builder()
                            .id(1L)
                            .name("North")
                            .totalSits(200L)
                            .build())
                  )).andExpect(MockMvcResultMatchers
                    .jsonPath("$.1").exists());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
