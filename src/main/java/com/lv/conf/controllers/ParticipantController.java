package com.lv.conf.controllers;

import com.lv.conf.models.Participant;
import com.lv.conf.models.ParticipantDto;
import com.lv.conf.services.ParticipantService;
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
@RequestMapping("/api/v1/participants")
public class ParticipantController {
    private static final Logger LOG = LoggerFactory.getLogger(ParticipantController.class);

    private final ParticipantService participantService;

    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @ApiOperation(value = "Get participant.")
        @ApiResponses({
            @ApiResponse(code = 200, message = "Participant retrieved"),
            @ApiResponse(code = 404, message = NOT_FOUND_STATUS_DESC),
            @ApiResponse(code = 500, message = INTERNAL_SERVER_ERROR_STATUS_DESC)})
        @GetMapping("/{id}")
        @ResponseStatus(HttpStatus.OK)
        public ResponseEntity<ParticipantDto> getParticipant(@PathVariable Long id) {
            LOG.info("Get Participant with id {}", id);
            return ResponseEntity.ok(participantService.getParticipant(id));
        }

        @ApiResponses({
            @ApiResponse(code = 201, message = "Participant was created"),
            @ApiResponse(code = 400, message = BAD_REQUEST_STATUS_DESC),
            @ApiResponse(code = 422, message = UNPROCESSABLE_ENTITY_DESC),
            @ApiResponse(code = 500, message = INTERNAL_SERVER_ERROR_STATUS_DESC)})
        @PostMapping
        public ResponseEntity<HttpStatus> createParticipant(@RequestBody Participant participant) {
            LOG.info("Create Participant {}", participant);
            String uri = String.format("/api/v1/conferences/%d",participantService.addParticipant(participant));

            return ResponseEntity.created(URI.create(uri)).build();
        }

        @ApiOperation(value = "Delete participant.")
        @ApiResponses({
            @ApiResponse(code = 204, message = "Participant was deleted"),
            @ApiResponse(code = 404, message = NOT_FOUND_STATUS_DESC),
            @ApiResponse(code = 422, message = UNPROCESSABLE_ENTITY_DESC),
            @ApiResponse(code = 500, message = INTERNAL_SERVER_ERROR_STATUS_DESC)})
        @DeleteMapping("/{id}")
        @ResponseStatus(HttpStatus.ACCEPTED)
        public ResponseEntity<Integer> deleteParticipant(@PathVariable Long id) {
            LOG.info("Delete Participant with id {}", id);
            participantService.deleteParticipant(id);

            return ResponseEntity.ok(204);
        }
}
