package com.lv.conf.services;

import com.lv.conf.exceptions.ParticipantException;
import com.lv.conf.exceptions.SitException;
import com.lv.conf.models.Participant;
import com.lv.conf.models.ParticipantDto;
import com.lv.conf.models.Sit;
import com.lv.conf.repositories.ParticipantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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

                List<Sit> sits = sitService.getSits(conference.getConference().getId(), conference.getConference().getRoomId());

                if (sits.contains(pt.getReservedSit())) {
                    throw new ParticipantException("The sits " + pt.getReservedSit() + " is reserved.");
                } else {
                    sitService.setSit(conference.getConference().getId(), pt.getRoomId(), pt.getReservedSit());
                }

                return ParticipantDto
                        .builder()
                        .firstName(pt.getFirstName())
                        .lastName(pt.getLastName())
                        .reservedSit(pt.getReservedSit())
                        .conference(conference.getConference())
                        .build();
            }
            throw new ParticipantException("Participant with id " + id + " not exists");
        }

        public Long addParticipant(Participant participant) {
            List<Sit> sits = sitService.getSits(participant.getConferenceId(), participant.getReservedSit());
            sits.forEach(sit -> {
                if (sit.getReservedSit() == participant.getReservedSit()) {
                    throw new ParticipantException("Sit with number " + participant.getReservedSit() + " was reserved.");
                }
            });
            return participantRepository.save(participant).getId();
        }

        public void deleteParticipant(Long id) {
            participantRepository.deleteById(id);
        }
}
