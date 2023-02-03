package com.lv.conf.repositories;

import com.lv.conf.models.Sit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SitRepository extends JpaRepository<Sit, Long> {
    List<Sit> findByConferenceIdAndReservedSit(Long conferenceId, Long roomId);
    //List<Sit> findByConferenceIdReservedSitAndSitId(Long conferenceId, Long roomId, Long sitId);
}
