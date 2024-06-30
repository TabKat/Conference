package com.lv.conf.services;

import com.lv.conf.dtos.ConferenceDto;
import com.lv.conf.models.Conference;
import com.lv.conf.repositories.ConferenceRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class ConferenceService {
    private final ConferenceRepository conferenceRepository;
    private final SitService sitService;

    @Autowired
    ConferenceService(ConferenceRepository conferenceRepository,
                      SitService sitService) {
        this.conferenceRepository = conferenceRepository;
        this.sitService = sitService;
    }

    public Optional<ConferenceDto> getConference(Long id) {
        log.info("Find conference with id {}", id);
        Optional<Conference> conf = conferenceRepository.findById(id);

        if (conf.isPresent()) {
            log.info("Conference with id {} was found", id);
            return Optional.of(ConferenceDto
                    .builder()
                    .conference(conf.get())
                    .sits(sitService.getSits(conf.get().getId(), conf.get().getRoomId()))
                    .build());
        }
        return Optional.empty();
    }

    @Transactional
    public Long addConference(Conference conference) {
        log.info("Add conference {}", conference);
        return conferenceRepository.save(conference).getId();
    }

    @Transactional
    public void deleteConference(Long conferenceId) {
        log.info("Delete conference with id {}", conferenceId);
        conferenceRepository.deleteById(conferenceId);
    }
}
