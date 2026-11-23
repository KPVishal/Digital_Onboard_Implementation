package com.bijlipay.repository;

import com.bijlipay.model.CounterManagement;
import com.bijlipay.model.LeadInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface CounterManagementRepository extends JpaRepository<CounterManagement, Long> {
    @Query("select c from CounterManagement c where c.leadInformation.leadId=?1")
    List<CounterManagement> getAllByLeads(long leadId);

    @Query("select c from CounterManagement c where c.id=?1 and c.leadInformation.leadId =?2")
    CounterManagement findByIds(long id, long leadId);


    @Query("select c.vpa from CounterManagement c where c.leadInformation.leadId=?1")
    List<String> getVpaById(long leadId);

    @Transactional
    @Modifying
    @Query("DELETE FROM CounterManagement WHERE id= ?1")
    void findId(long id);

    @Query("select c from CounterManagement c where c.vpa LIKE ?1 and c.leadInformation.leadId =?2")
    CounterManagement findByVpa(String sqrVpa, long leadId);

    @Query("select c from CounterManagement c where soundBoxSno=?1 and c.leadInformation.leadId =?2")
    CounterManagement findBySerialNo(String serialNumber, long leadId);
    @Query("select c from CounterManagement c where c.deviceSerialNo=?1 and c.leadInformation.leadId =?2")
    CounterManagement findBySerialNoDevice(String serialNumber, long leadId);

    @Query("select c from CounterManagement c where c.soundBoxSno IS NOT NULL and c.leadInformation =?1")
    List<CounterManagement> findByLeadId(LeadInformation leadInformation);
    @Query("select c.vpa from CounterManagement c where c.deviceType LIKE ?1 and c.leadInformation.leadId =?2")
    List<String>  findByDevice(String device, long leadId);

    @Query("select c from CounterManagement c where c.deviceSerialNo IS NOT NULL and c.leadInformation =?1")
    List<CounterManagement> findBydeviceNo(LeadInformation leadInformation);
}
