package com.lv.conf.controllers;

import com.lv.conf.models.Conference;
import com.lv.conf.models.ConferenceDto;
import com.lv.conf.services.ConferenceService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static com.lv.conf.config.Constants.*;

@RestController
@RequestMapping("/api/v1/conferences")
public class ConferenceController {

    final private ConferenceService conferenceService;

    public ConferenceController(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

    @ApiOperation(value = "Get conference.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Conference retrieved"),
            @ApiResponse(code = 404, message = NOT_FOUND_STATUS_DESC),
            @ApiResponse(code = 500, message = INTERNAL_SERVER_ERROR_STATUS_DESC)})

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ConferenceDto> getConference(@PathVariable Long id) {
        return ResponseEntity.ok(conferenceService.getConference(id));
    }

    @ApiResponses({
            @ApiResponse(code = 201, message = "Conference was created"),
            @ApiResponse(code = 400, message = BAD_REQUEST_STATUS_DESC),
            @ApiResponse(code = 422, message = UNPROCESSABLE_ENTITY_DESC),
            @ApiResponse(code = 500, message = INTERNAL_SERVER_ERROR_STATUS_DESC)})
    @PostMapping
    public ResponseEntity createConference(@RequestBody Conference conference) {
        String uri = String.format("/api/v1/conferences/%d", conferenceService.addConference(conference));
        return ResponseEntity.created(URI.create(uri)).build();
    }

    @ApiOperation(value = "Delete conference.")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Conference was deleted"),
            @ApiResponse(code = 404, message = NOT_FOUND_STATUS_DESC),
            @ApiResponse(code = 422, message = UNPROCESSABLE_ENTITY_DESC),
            @ApiResponse(code = 500, message = INTERNAL_SERVER_ERROR_STATUS_DESC)})
    @DeleteMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity deleteConference(@RequestParam(name = "conferenceId")
                                     Long conferenceId) {
        conferenceService.deleteConference(conferenceId);
        return ResponseEntity.ok(204);
    }
}
