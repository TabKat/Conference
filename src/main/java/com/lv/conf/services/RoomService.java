package com.lv.conf.services;

import com.lv.conf.exceptions.RoomException;
import com.lv.conf.models.Room;
import com.lv.conf.repositories.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomService {
    final private RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room getRoom(Long id) {
        var room = roomRepository.findById(id);
        if (room.isPresent()) {
            return room.get();
        }
        throw new RoomException("Room with id " + id + " does not exists");
    }

    public Long addRoom(Room room) {
        return roomRepository.save(room).getId();
    }

    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }

}
