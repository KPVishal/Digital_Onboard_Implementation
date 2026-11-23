package com.bijlipay.repository;

import com.bijlipay.model.LeadInformation;
import com.bijlipay.model.MarsOnboardingInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface MarsOnboardingRepository extends JpaRepository<MarsOnboardingInformation,Long> {


    @Query("SELECT m FROM MarsOnboardingInformation m WHERE m.applicationNumber = ?1")
    MarsOnboardingInformation findByApplicationNumber(String applicationNumber);

    @Query("SELECT m FROM MarsOnboardingInformation m WHERE m.leadInformation.leadId = ?1")
    MarsOnboardingInformation findLeadId(long leadId);

    @Transactional
    @Modifying
    @Query("DELETE FROM MarsOnboardingInformation WHERE leadInformation = ?1")
    void deleteDuplicate(LeadInformation leadInformation);

    @Query("select m from MarsOnboardingInformation m where m.qrApplicationNumber =?1")
    MarsOnboardingInformation findByQrApplicationNumber(String applicationNumber);



}
