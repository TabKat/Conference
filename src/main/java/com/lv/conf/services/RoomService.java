package com.lv.conf.services;

import com.lv.conf.exceptions.RoomException;
import com.lv.conf.models.Room;
import com.lv.conf.repositories.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class RoomService {
    private static final Logger LOG = LoggerFactory.getLogger(RoomService.class);
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Transactional
    public Room getRoom(Long id) {
        LOG.info("Get room with id {}", id);
        Optional<Room> room = roomRepository.findById(id);

        if (room.isPresent()) {
            LOG.info("Room with id {} was found", room);
            return room.get();
        }
        throw new RoomException("Room with id " + id + " does not exist.");
    }

    public Long addRoom(Room room) {
        LOG.info("Add room {}", room);
        return roomRepository.save(room).getId();
    }

    public void deleteRoom(Long id) {
        LOG.info("Delete room with id {}", id);
        roomRepository.deleteById(id);
    }

}
