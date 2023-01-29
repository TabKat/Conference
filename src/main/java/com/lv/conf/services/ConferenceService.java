package com.lv.conf.services;

import com.lv.conf.exceptions.ConferenceException;
import com.lv.conf.models.Conference;
import com.lv.conf.repositories.ConferenceRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConferenceService {

    final private ConferenceRepository conferenceRepository;

    ConferenceService(ConferenceRepository conferenceRepository) {
        this.conferenceRepository = conferenceRepository;
    }
    public Conference getConference(Long id) {
        var conf = conferenceRepository.findById(id);
        if (conf.isPresent()) {
            return conf.get();
        }
        throw new ConferenceException("Conference with id " + id + " does not exists.");
    }

    public Long addConference(Conference conference) {
        return conferenceRepository.save(conference).getId();
    }

    public void deleteConference(Long conferenceId) {
        conferenceRepository.deleteById(conferenceId);
    }
}
