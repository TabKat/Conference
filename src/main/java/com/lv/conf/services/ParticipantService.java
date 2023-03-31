package com.lv.conf.services;

import com.lv.conf.exceptions.ParticipantException;
import com.lv.conf.models.Participant;
import com.lv.conf.models.ParticipantDto;
import com.lv.conf.models.Sit;
import com.lv.conf.repositories.ParticipantRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParticipantService {
    private static final Logger LOG = LoggerFactory.getLogger(ParticipantService.class);
    private final ParticipantRepository participantRepository;
    private final ConferenceService conferenceService;
    private final SitService sitService;

    public ParticipantService(ParticipantRepository participantRepository,
                              ConferenceService conferenceService,
                              SitService sitService) {
        this.participantRepository = participantRepository;
        this.conferenceService = conferenceService;
        this.sitService = sitService;
    }

    @Transactional
    public ParticipantDto getParticipant(Long id) {
        LOG.info("Find participant with id {}", id);
        Optional<Participant> participant = participantRepository.findById(id);

        if (participant.isPresent()) {
            LOG.info("Participant with id {} was found", id);
            var pt = participant.get();
            LOG.info("Find participant conference with id {}", pt.getConferenceId());
            var conference = conferenceService.getConference(pt.getConferenceId());

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

    @Transactional
    public Long addParticipant(Participant participant) {
        LOG.info("Add participant {}", participant);
        LOG.info("Find all reserved sits for conference id {}", participant.getConferenceId());
        List<Sit> sits = sitService.getSits(participant.getConferenceId(), participant.getReservedSit());
        sits.forEach(sit -> {
            LOG.info("Checking if selected by participant sit was reserved");
            if (sit.getReservedSit().equals(participant.getReservedSit())) {
                throw new ParticipantException("Sit with number " + participant.getReservedSit() + " was reserved.");
            }
        });

        LOG.info("Reserve sit for participant with id {} for conference id {}, and with room id {}",
            participant.getConferenceId(),
            participant.getRoomId(),
            participant.getReservedSit());
        sitService.reserveSit(participant.getConferenceId(),
            participant.getRoomId(),
            participant.getReservedSit());

        LOG.info("Participant was added");
        return participantRepository.save(participant).getId();
    }

    @Transactional
    public void deleteParticipant(Long id) {
        LOG.info("Delete participant with id {}", id);
        Participant pt = participantRepository.getReferenceById(id);
        LOG.info("Delete reserved sit for participant with id {}", id);
        sitService.deleteSit(pt.getConferenceId(), pt.getReservedSit());
        LOG.info("Delete participant");
        participantRepository.deleteById(id);
    }
}
