package com.lv.conf.controllers;

import com.lv.conf.models.Participant;
import com.lv.conf.models.ParticipantDto;
import com.lv.conf.services.ParticipantService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.lv.conf.config.Constants.*;

@RestController
@RequestMapping("/api/v1/participant")
public class ParticipantController {
    final private ParticipantService participantService;

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
            return ResponseEntity.ok(participantService.getParticipant(id));
        }

        @ApiResponses({
                @ApiResponse(code = 202, message = "Participant was created"),
                @ApiResponse(code = 400, message = BAD_REQUEST_STATUS_DESC),
                @ApiResponse(code = 422, message = UNPROCESSABLE_ENTITY_DESC),
                @ApiResponse(code = 500, message = INTERNAL_SERVER_ERROR_STATUS_DESC)})
        @PostMapping
        public ResponseEntity<Long> createParticipant(@RequestBody Participant participant) {
            return ResponseEntity.ok(participantService.addParticipant(participant));
        }

        @ApiOperation(value = "Delete participant.")
        @ApiResponses({
                @ApiResponse(code = 202, message = "Participant was deleted"),
                @ApiResponse(code = 404, message = NOT_FOUND_STATUS_DESC),
                @ApiResponse(code = 422, message = UNPROCESSABLE_ENTITY_DESC),
                @ApiResponse(code = 500, message = INTERNAL_SERVER_ERROR_STATUS_DESC)})
        @DeleteMapping("/{id}")
        @ResponseStatus(HttpStatus.ACCEPTED)
        public ResponseEntity<?> deleteParticipant(@PathVariable Long id) {
            participantService.deleteParticipant(id);
            return ResponseEntity.ok(204);
        }
}
