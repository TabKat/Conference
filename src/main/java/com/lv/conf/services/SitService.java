package com.lv.conf.services;

import com.lv.conf.models.Sit;
import com.lv.conf.repositories.SitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SitService {
    private final SitRepository sitRepository;

    public SitService(SitRepository sitRepository) {
        this.sitRepository = sitRepository;
    }

    public void setSit(Long conferenceId, Long roomId, Long sitId) {
        Sit s = Sit
                .builder()
                .reservedSit(sitId)
                .conferenceId(conferenceId)
                .roomId(roomId)
                .build();
        sitRepository.save(s);
    }

    public List<Sit> getSits(Long conferenceId, Long roomId) {
        return sitRepository.findByConferenceIdAndReservedSit(conferenceId, roomId);
    }

    public void deleteSit(Long conferenceId, Long sitId) {
        sitRepository.deleteByConferenceIdAndReservedSit(conferenceId, sitId);
    }
}
