package com.lv.conf.services;

import com.lv.conf.exceptions.ConferenceException;
import com.lv.conf.models.Conference;
import com.lv.conf.models.ConferenceDto;
import com.lv.conf.repositories.ConferenceRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ConferenceService {
    private static final Logger LOG = LoggerFactory.getLogger(ConferenceService.class);

    final private ConferenceRepository conferenceRepository;
    private final SitService sitService;

    ConferenceService(ConferenceRepository conferenceRepository,
                      SitService sitService) {
        this.conferenceRepository = conferenceRepository;
        this.sitService = sitService;
    }

    @Transactional
    public ConferenceDto getConference(Long id) {
        LOG.info("Trying to find conference with id {}...", id);
        var conf = conferenceRepository.findById(id);
        if (conf.isPresent()) {
            LOG.info("Conference with id {} is presented", id);
            return ConferenceDto
                .builder()
                .conference(conf.get())
                .sits(sitService.getSits(conf.get().getId(), conf.get().getRoomId()))
                .build();
        }
        throw new ConferenceException("Conference with id " + id + " does not exists.");
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
