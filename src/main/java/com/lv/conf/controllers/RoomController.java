package com.lv.conf.controllers;

import com.lv.conf.models.Room;
import com.lv.conf.services.RoomService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static com.lv.conf.config.Constants.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {
    private static final Logger LOG = LoggerFactory.getLogger(RoomController.class);
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @ApiOperation(value = "Get Room.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Conference retrieved"),
        @ApiResponse(code = 404, message = NOT_FOUND_STATUS_DESC),
        @ApiResponse(code = 500, message = INTERNAL_SERVER_ERROR_STATUS_DESC)})
    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoom(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.getRoom(id));
    }

    @ApiResponses({
        @ApiResponse(code = 201, message = "Room was created"),
        @ApiResponse(code = 400, message = BAD_REQUEST_STATUS_DESC),
        @ApiResponse(code = 422, message = UNPROCESSABLE_ENTITY_DESC),
        @ApiResponse(code = 500, message = INTERNAL_SERVER_ERROR_STATUS_DESC)})
    @PostMapping
    public ResponseEntity<HttpStatus> createRoom(@RequestBody Room room) {
        String uri = String.format("/api/v1/rooms/%d", roomService.addRoom(room));

        LOG.info("Room was created and can be accessed via uri {}", uri);
        return ResponseEntity.created(URI.create(uri)).build();
    }

    @ApiOperation(value = "Delete room.")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Room was deleted"),
        @ApiResponse(code = 404, message = NOT_FOUND_STATUS_DESC),
        @ApiResponse(code = 422, message = UNPROCESSABLE_ENTITY_DESC),
        @ApiResponse(code = 500, message = INTERNAL_SERVER_ERROR_STATUS_DESC)})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Integer> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);

        LOG.info("Room with id {} was successfully deleted", id);
        return ResponseEntity.ok(204);
    }

}
