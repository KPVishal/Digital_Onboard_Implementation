package com.bijlipay.repository;

import com.bijlipay.model.LeadInformation;
import com.bijlipay.model.LeadStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface LeadInformationRepository extends JpaRepository<LeadInformation, Long> {

    @Query("select l from LeadInformation l where l.leadId =?1")
    LeadInformation findLeadId(long leadId);

    @Query(value = "SELECT l FROM LeadInformation l WHERE l.applicationNumber = ?1")
    LeadInformation getLeadByApplicationNumber(String applicationNumber);
    @Query("SELECT l FROM LeadInformation l WHERE l.leadId =?1")
    LeadInformation findLead(long leadId);

    @Query("select l from LeadInformation l where l.qrApplicationNumber =?1")
    LeadInformation getLeadByQrApplicationNumber(String applicationNumber);

    @Query("SELECT l FROM LeadInformation l WHERE l.leadNumber =?1")
    LeadInformation findLeadNumber(String leadNumber);

    @Query("select l from LeadInformation l where l.leadSource.leadSourceName = 'LS_Wowpe'")
    List<LeadInformation> findByLeadSourceWowpe();

}
