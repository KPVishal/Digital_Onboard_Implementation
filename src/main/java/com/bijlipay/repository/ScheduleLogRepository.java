package com.bijlipay.repository;

import com.bijlipay.model.LeadInformation;
import com.bijlipay.model.ScheduleLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ScheduleLogRepository extends JpaRepository<ScheduleLogs,Long> {

    @Query("SELECT s FROM ScheduleLogs s WHERE s.leadInformation =?1")
    List<ScheduleLogs> getLogs(LeadInformation leadInformation);
}
