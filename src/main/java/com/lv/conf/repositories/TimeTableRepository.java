package com.lv.conf.repositories;

import com.lv.conf.models.TimeTable;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface TimeTableRepository extends JpaRepository<TimeTable, Long> {
    TimeTable findByConferenceId(String conferenceId);
}
