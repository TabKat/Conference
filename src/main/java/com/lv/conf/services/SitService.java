package com.lv.conf.services;

import com.lv.conf.exceptions.RoomException;
import com.lv.conf.exceptions.SitException;
import com.lv.conf.models.Room;
import com.lv.conf.models.Sit;
import com.lv.conf.repositories.SitRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class SitService {
    private static final Logger LOG = LoggerFactory.getLogger(SitService.class);
    private final SitRepository sitRepository;

    private final RoomService roomService;

    public SitService(SitRepository sitRepository, RoomService roomService) {
        this.sitRepository = sitRepository;
        this.roomService = roomService;
    }

    @Transactional
    public void reserveSit(Long conferenceId, Long roomId, Long sitNumber) {
        LOG.info("Check if selected sit number {} is valid", sitNumber);
        Optional<Room> room = roomService.getRoom(roomId);

        if (room.isEmpty()) {
            throw new RoomException("The room with id " + roomId + " is not exists");
        }

        Integer totalSitsInRoom = room.get().getTotalSits();

        if (sitNumber > totalSitsInRoom) {
            throw new SitException("The sit number should be less than " + totalSitsInRoom);
        }

        LOG.info("Reserve sit with conference id {} with room id {}, and sit number {}", conferenceId, roomId, sitNumber);
        Sit s = Sit
            .builder()
            .reservedSit(sitNumber)
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
