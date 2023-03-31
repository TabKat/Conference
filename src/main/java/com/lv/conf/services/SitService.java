package com.lv.conf.services;

import com.lv.conf.models.Sit;
import com.lv.conf.repositories.SitRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SitService {
    private static final Logger LOG = LoggerFactory.getLogger(SitService.class);
    private final SitRepository sitRepository;

    public SitService(SitRepository sitRepository) {
        this.sitRepository = sitRepository;
    }

    @Transactional
    public void reserveSit(Long conferenceId, Long roomId, Long sitId) {
        LOG.info("Reserve sit with conference id {} with room id {}, and sit id {}", conferenceId, roomId, sitId);
        Sit s = Sit
            .builder()
            .reservedSit(sitId)
            .conferenceId(conferenceId)
            .roomId(roomId)
            .build();
        LOG.info("Save sit {}", s);
        sitRepository.save(s);
    }

    public List<Sit> getSits(Long conferenceId, Long roomId) {
        LOG.info("Get sit with conference id {}, and room id {}", conferenceId, roomId);
        return sitRepository.findByConferenceIdAndReservedSit(conferenceId, roomId);
    }

    public void deleteSit(Long conferenceId, Long sitId) {
        LOG.info("Delete sit for conference with id {}, and sit id {}", conferenceId, sitId);
        sitRepository.deleteByConferenceIdAndReservedSit(conferenceId, sitId);
    }
}
