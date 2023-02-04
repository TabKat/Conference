package com.lv.conf.repositories;

import com.lv.conf.models.Sit;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Transactional
@Repository
public interface SitRepository extends JpaRepository<Sit, Long> {
    List<Sit> findByConferenceIdAndReservedSit(Long conferenceId, Long roomId);
    void deleteByConferenceIdAndReservedSit(Long conferenceId, Long roomId);
}
