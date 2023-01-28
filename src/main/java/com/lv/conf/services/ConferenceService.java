package com.lv.conf.services;

import com.lv.conf.models.Conference;
import com.lv.conf.repositories.ConferenceRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConferenceService {

    private ConferenceRepository conferenceRepository;

    ConferenceService(ConferenceRepository conferenceRepository) {
        this.conferenceRepository = conferenceRepository;
    }
    public Optional<Conference> getConference(Long id) {
        return conferenceRepository.findById(id);
    }

    @Transactional
    public Long addConference(Conference conference) {
        return conferenceRepository.save(conference).getId();
    }

    public void deleteConference(Long conferenceId) {
        conferenceRepository.deleteById(conferenceId);
    }
}
