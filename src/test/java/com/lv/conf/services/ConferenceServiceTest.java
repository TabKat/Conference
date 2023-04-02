package com.lv.conf.services;

import com.lv.conf.models.Conference;
import com.lv.conf.dtos.ConferenceDto;
import com.lv.conf.models.TimeTable;
import com.lv.conf.repositories.ConferenceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConferenceServiceTest {

    @Mock
    private ConferenceRepository conferenceRepository;

    @Mock
    private SitService sitService;

    private Conference conference;

    private ConferenceService conferenceService;

    @BeforeEach
    void setup() {
        List<TimeTable> tm = new ArrayList<>();
        tm.add(TimeTable
            .builder()
            .conferenceId("123-456")
            .startDate(LocalDateTime.of(2023, Month.MAY, 5, 2, 10, 0))
            .endDate(LocalDateTime.of(2023, Month.MAY, 5, 2, 18, 0))
            .build());

        conference = Conference
            .builder()
            .id(1L)
            .name("Spring Boot 2023")
            .roomId(55L)
            .timeTable(tm)
            .build();

        conferenceService = new ConferenceService(conferenceRepository, sitService);
    }

    @Test
    void shouldGetConference() {
        when(conferenceRepository.findById(1L)).thenReturn(Optional.of(conference));

        ConferenceDto dto = conferenceService.getConference(1L);

        assertThat(dto.getConference().getId(), equalTo(1L));
        assertThat(dto.getConference().getName(), equalTo("Spring Boot 2023"));
        assertThat(dto.getConference().getRoomId(), equalTo(55L));

        var timeTable = dto.getConference().getTimeTable().get(0);
        assertThat(timeTable.getConferenceId(), equalTo("123-456"));
        assertThat(timeTable.getStartDate(), equalTo(LocalDateTime.parse("2023-05-05T02:10")));
        assertThat(timeTable.getEndDate(), equalTo(LocalDateTime.parse("2023-05-05T02:18")));
    }

    @Test
    void shouldAddConference() {
        when(conferenceRepository.save(conference)).thenReturn(conference);
        assertThat(conferenceService.addConference(conference), equalTo(1L));
    }
}