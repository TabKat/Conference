package com.lv.conf.repositories;

import com.lv.conf.models.Sit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SitRepository extends JpaRepository<Sit, Long> {
    Sit findByConferenceIdAndReservedSit(Long conferenceId, Long reservedSit);
}
