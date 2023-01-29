package com.lv.conf.services;

import com.lv.conf.exceptions.ParticipantException;
import com.lv.conf.models.Participant;
import com.lv.conf.models.ParticipantDto;
import com.lv.conf.repositories.ParticipantRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParticipantService {
    final private ParticipantRepository participantRepository;
    final private ConferenceService conferenceService;
    private final SitService sitService;

    public ParticipantService(ParticipantRepository participantRepository,
                              ConferenceService conferenceService,
                              SitService sitService) {
        this.participantRepository = participantRepository;
        this.conferenceService = conferenceService;
        this.sitService = sitService;
    }

        public ParticipantDto getParticipant(Long id) {
            Optional<Participant> participant = participantRepository.findById(id);

            if (participant.isPresent()) {
                var pt = participant.get();
                var conference = conferenceService
                        .getConference(pt.getConferenceId());

                sitService.reserveSit(pt);

                return ParticipantDto
                        .builder()
                        .firstName(pt.getFirstName())
                        .lastName(pt.getLastName())
                        .sitNumber(pt.getReservedSit())
                        .conference(conference)
                        .build();
            }
            throw new ParticipantException("Participant with id " + id + " not exists");
        }

        public Long addParticipant(Participant room) {
            return participantRepository.save(room).getId();
        }

        public void deleteParticipant(Long id) {
            participantRepository.deleteById(id);
        }
}
