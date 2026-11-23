package com.bijlipay.repository;

import com.bijlipay.model.LeadStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LeadStatusRepository extends JpaRepository<LeadStatus,Long> {

    @Query("select l from LeadStatus l where l.leadStatusId=?1")
    LeadStatus fetchLeadStatusById(long id);


}
