package com.lv.conf.services;

import com.lv.conf.dtos.ConferenceDto;
import com.lv.conf.models.Conference;
import com.lv.conf.repositories.ConferenceRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class ConferenceService {
    private static final Logger LOG = LoggerFactory.getLogger(ConferenceService.class);

    private final ConferenceRepository conferenceRepository;
    private final SitService sitService;

    ConferenceService(ConferenceRepository conferenceRepository,
                      SitService sitService) {
        this.conferenceRepository = conferenceRepository;
        this.sitService = sitService;
    }

    @Transactional
    public Optional<ConferenceDto> getConference(Long id) {
        LOG.info("Find conference with id {}", id);
        Optional<Conference> conf = conferenceRepository.findById(id);

        if (conf.isPresent()) {
            LOG.info("Conference with id {} was found", id);
            return Optional.of(ConferenceDto
                    .builder()
                    .conference(conf.get())
                    .sits(sitService.getSits(conf.get().getId(), conf.get().getRoomId()))
                    .build());
        }
        return Optional.empty();
    }

    public Long addConference(Conference conference) {
        LOG.info("Add conference {}", conference);
        return conferenceRepository.save(conference).getId();
    }

    public void deleteConference(Long conferenceId) {
        LOG.info("Delete conference with id {}", conferenceId);
        conferenceRepository.deleteById(conferenceId);
    }
}
