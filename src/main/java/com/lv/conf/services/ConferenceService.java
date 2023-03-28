package com.lv.conf.services;

import com.lv.conf.exceptions.ConferenceException;
import com.lv.conf.models.Conference;
import com.lv.conf.models.ConferenceDto;
import com.lv.conf.repositories.ConferenceRepository;
import org.springframework.stereotype.Service;

@Service
public class ConferenceService {

    final private ConferenceRepository conferenceRepository;
    private final SitService sitService;

    ConferenceService(ConferenceRepository conferenceRepository,
                      SitService sitService) {
        this.conferenceRepository = conferenceRepository;
        this.sitService = sitService;
    }
    public ConferenceDto getConference(Long id) {
        var conf = conferenceRepository.findById(id);
        if (conf.isPresent()) {
            return ConferenceDto
                .builder()
                .conference(conf.get())
                .sits(sitService.getSits(conf.get().getId(), conf.get().getRoomId()))
                .build();
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
