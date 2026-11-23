package com.bijlipay.repository;

import com.bijlipay.model.LeadDocuments;
import com.bijlipay.model.LeadInformation;
import com.bijlipay.model.UploadedDocuments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UploadedDocumentRepository extends JpaRepository<UploadedDocuments,Long> {

    @Query("SELECT u FROM UploadedDocuments u WHERE u.leadDocuments IN (?1)")
    List<UploadedDocuments> findByLeadDocumentId(List<LeadDocuments> leadDocuments);

    @Query("SELECT u.leadDocuments FROM UploadedDocuments u WHERE u.leadDocuments.leadInformation=?1")
    List<LeadDocuments> findByLeadById(LeadInformation leadInformation);
}
