package com.lv.conf.services;

import com.lv.conf.models.Participant;
import com.lv.conf.models.Sit;
import com.lv.conf.repositories.SitRepository;
import org.springframework.stereotype.Service;

@Service
public class SitService {
    private final SitRepository sitRepository;

    public SitService(SitRepository sitRepository) {
        this.sitRepository = sitRepository;
    }

    public void reserveSit(Participant pt) {

        var sit = Sit.builder()
                    .reservedSit(pt.getReservedSit())
                    .conferenceId(pt.getConferenceId())
                    .roomId(pt.getRoomId())
                    .build();
        sitRepository.save(sit);

    }
}
