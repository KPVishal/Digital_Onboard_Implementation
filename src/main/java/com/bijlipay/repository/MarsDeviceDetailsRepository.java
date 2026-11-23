package com.bijlipay.repository;

import com.bijlipay.model.LeadInformation;
import com.bijlipay.model.LeadStatus;
import com.bijlipay.model.MarsDeviceDetails;
import com.bijlipay.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

import static com.bijlipay.config.Constants.*;

@Repository
public interface MarsDeviceDetailsRepository extends JpaRepository<MarsDeviceDetails, Long> {

    @Query("SELECT COUNT(m) FROM MarsDeviceDetails m WHERE m.applicationNumber=?1 AND m.deviceStatus = 1")
    int findByApplicationNumberCount(String applicationNumber);

    @Transactional
    @Modifying
    @Query("DELETE FROM MarsDeviceDetails WHERE applicationNumber=?1")
    void deleteDuplicateEntry(String applicationNumber);

    @Query(value = "SELECT m FROM MarsDeviceDetails m WHERE m.leadInformation =?1 AND m.createdBy=?2 AND m"
            + ".deviceStatus IN (" + MARS_DEVICE_STATUS_TID_GENERATED + "," + MARS_DEVICE_STATUS_SCAN_PICKED + "," + MARS_DEVICE_STATUS_INSTALLATION_SCHEDULED + "," + MARS_DEVICE_STATUS_INSTALLATION_RE_SCHEDULED + "," + MARS_DEVICE_STATUS_IMPLEMENTED + " )")
    List<MarsDeviceDetails> findByImplementationDetailsApp(LeadInformation leadInformation, User sessionUser);

    @Query("SELECT m FROM MarsDeviceDetails m WHERE m.id =?1 and m.sqrVpa IS NULL")
    MarsDeviceDetails findId(Long id);

    @Query("SELECT m FROM MarsDeviceDetails m WHERE m.id =?1")
    MarsDeviceDetails findId2(Long id);

    @Query("SELECT m FROM MarsDeviceDetails m WHERE m.leadInformation.leadId =?1 and m.sqrVpa IS NOT NULL")
    MarsDeviceDetails findId1(Long id);
    @Query("SELECT m FROM MarsDeviceDetails m WHERE m.leadInformation.leadId =?1 and m.sqrVpa IS NOT NULL")
    List<MarsDeviceDetails> findLeadQr(Long id);

    @Query("SELECT m FROM MarsDeviceDetails m WHERE m.id =?1")
    List<MarsDeviceDetails> findIdByIds(Long id);

    @Query("SELECT m FROM MarsDeviceDetails m WHERE m.id =?1")
    MarsDeviceDetails findTidById(Long id);

    @Query("SELECT m FROM MarsDeviceDetails m WHERE m.leadInformation.leadId =?1 and m.sqrVpa IS NULL")
    MarsDeviceDetails findMarsData(Long id);

    @Query("SELECT m FROM MarsDeviceDetails m WHERE m.leadInformation =?1")
    List<MarsDeviceDetails> findLeadId(LeadInformation leadInformation);

    @Query("SELECT m FROM MarsDeviceDetails m WHERE m.leadInformation =?1 and m.sqrVpa IS NULL")
    List<MarsDeviceDetails> findLeadPOS(LeadInformation leadInformation);

    @Query("SELECT m FROM MarsDeviceDetails m WHERE m.leadInformation =?1")
    MarsDeviceDetails findMarsDataByLeadId(LeadInformation leadInformation);

    @Query("select m from MarsDeviceDetails m where m.tid =?1")
    MarsDeviceDetails findTid(String tid);

    @Query("select m from MarsDeviceDetails m where m.tid =?1")
    MarsDeviceDetails findVpa(String tid);

    @Query(value = "SELECT * FROM mars_device_details m WHERE m.lead_id =?1 ORDER BY m.created_at DESC LIMIT 1", nativeQuery = true)
    MarsDeviceDetails findByLeadId(LeadInformation leadInformation1);

    @Query("SELECT m FROM MarsDeviceDetails m WHERE m.leadInformation.leadId =?1 and m.sqrVpa IS NULL")
    MarsDeviceDetails findLead(long leadId);

    @Query("SELECT m FROM MarsDeviceDetails m WHERE m.id =?1")
    List<MarsDeviceDetails> findIds(long id);

    @Query("SELECT COUNT(m) FROM MarsDeviceDetails m WHERE m.leadInformation.leadId=?1 and m.sqrVpa IS NOT NULL ")
    int findCount(long leadId);

    @Query("SELECT COUNT(m) FROM MarsDeviceDetails m WHERE m.leadInformation.leadId=?1")
    int findCountAll(long leadId);
    @Query("SELECT COUNT(m) FROM MarsDeviceDetails m WHERE m.leadInformation.leadId=?1 and m.sqrVpa IS NULL")
    int findCountAllPOS(long leadId);

    @Query(value = "SELECT * FROM mars_device_details m WHERE m.lead_id =?1 ORDER BY m.created_at ASC LIMIT 1", nativeQuery = true)
    MarsDeviceDetails findByList(long leadInformation);

    @Query("SELECT m FROM MarsDeviceDetails m WHERE m.leadInformation =?1")
    MarsDeviceDetails findLeadById(LeadInformation leadInformation);

    @Query("SELECT COUNT(m) FROM MarsDeviceDetails m WHERE m.leadInformation.leadId=?1 and m.deviceStatus=5")
    int findCountAllByImpl(long leadId);

    @Query(value = "SELECT l.lead_number,l.aggregator,l.merchant_name,m.mid,m.tid,l.created_at,l.lead_approved_date ,ls.lead_source_name from mars_device_details as m join lead_information as l join lead_source ls where  m.lead_id=l.lead_id and l.lead_source=ls.lead_source_id  and  l.created_at like ?1%",nativeQuery = true)
    List<Object[]> newLead(String formattedDate);

    @Query(value = "SELECT l.lead_number,l.aggregator,l.merchant_name,m.mid,m.tid,m.created_at,l.lead_approved_date ,ls.lead_source_name from mars_device_details as m join lead_information as l join lead_source ls where  m.lead_id=l.lead_id and l.lead_source=ls.lead_source_id and m.created_at  like ?1%",nativeQuery = true)
    List<Object[]> tidcreated(String formattedDate);

    @Query(value = "select l.lead_number,l.aggregator,l.merchant_name,m.mid,m.tid,l.updated_at,l.lead_approved_date , ls.lead_source_name ,lls.lead_status_name from mars_device_details as m join lead_information as l join lead_source as ls join lead_status as lls where  m.lead_id=l.lead_id and l.lead_source=ls.lead_source_id and l.lead_status = lls.lead_status_id and lls.lead_status_id in (10,6,9) and  l.updated_at like ?1%",nativeQuery = true)
    List<Object[]> leadReject(String formattedDate);

    @Query(value =  "select l.lead_number,l.aggregator,l.merchant_name,m.mid,m.tid,l.lead_approved_date,l.updated_at, ls.lead_source_name  from  mars_device_details as m join lead_information as l join lead_source as ls  where  m.lead_id=l.lead_id and l.lead_source=ls.lead_source_id and l.document_status in (2,4) and l.updated_at like ?1%",nativeQuery = true)
    List<Object[]> referback(String formattedDate);

}
