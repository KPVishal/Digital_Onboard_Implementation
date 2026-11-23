package com.bijlipay.repository;

import com.bijlipay.model.LeadInformation;
import com.bijlipay.model.VpaQr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface VpaQrRepository extends JpaRepository<VpaQr,Long> {


    @Query(value = "SELECT q FROM VpaQr q WHERE q.leadInformation.leadId = ?1")
    List<VpaQr> getScannedList(long id);



    @Query("SELECT q FROM VpaQr q WHERE q.leadInformation.leadId = ?1")
    VpaQr findID(long leadId);

    @Query("SELECT q.vpa FROM VpaQr q WHERE q.leadInformation.leadId = ?1")
    List<String> findID1(long leadId);
    @Query("SELECT q.vpa FROM VpaQr q WHERE q.leadInformation.leadId = ?1 and q.isSoundBox=0")
    List<String> findStickerVpa(long leadId);
    @Query("SELECT q.vpa FROM VpaQr q WHERE q.leadInformation.leadId = ?1 and q.isSoundBox=1")
    List<String> findSoundBoxVpa(long leadId);

    @Query("SELECT q FROM VpaQr q WHERE q.vpa=?1")
    VpaQr getLead(String vpa);

    @Query("select q from VpaQr q WHERE q.leadInformation.leadId = ?1 and q.is_Active=1")
    int getCount(LeadInformation leadInformation);

    @Transactional
    @Modifying
    @Query("delete from VpaQr ad where ad.vpa=?1")
    void deleteVpa(String vpa);
}
