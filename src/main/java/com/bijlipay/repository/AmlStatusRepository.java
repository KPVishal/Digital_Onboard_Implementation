package com.bijlipay.repository;

import com.bijlipay.model.AmlStatus;
import com.bijlipay.model.LeadInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AmlStatusRepository extends JpaRepository<AmlStatus, Long> {
    Optional<AmlStatus> findByLead(LeadInformation lead);
}
