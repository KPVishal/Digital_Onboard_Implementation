package com.bijlipay.repository;

import com.bijlipay.model.ConsentVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsentVerificationRepository extends JpaRepository<ConsentVerification,Long> {
    @Query("select m from ConsentVerification m where m.LeadId.leadId=?1")
    ConsentVerification getConsentDetailsById(long leadId);
}
