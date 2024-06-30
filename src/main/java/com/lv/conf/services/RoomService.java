package com.lv.conf.services;

import com.lv.conf.models.Room;
import com.lv.conf.repositories.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class RoomService {
    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Optional<Room> getRoom(Long id) {
        log.info("Get room with id {}", id);
        return roomRepository.findById(id);
    }

    @Transactional
    public Long addRoom(Room room) {
        log.info("Add room {}", room);
        return roomRepository.save(room).getId();
    }

    @Transactional
    public void deleteRoom(Long id) {
        log.info("Delete room with id {}", id);
        roomRepository.deleteById(id);
    }

}
