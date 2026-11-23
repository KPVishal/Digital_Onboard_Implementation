package com.bijlipay.repository;

import com.bijlipay.model.DocumentMapping;
import com.bijlipay.model.LeadDocuments;
import com.bijlipay.model.LeadInformation;
import com.bijlipay.model.SubDocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeadDocumentRepository extends JpaRepository<LeadDocuments, Integer> {


    @Query("select l from LeadDocuments l where l.leadInformation = ?1 and l.documentVerifiedStatus not in (?2)")
    List<LeadDocuments> findDocumentsByLeadId(LeadInformation leadInformation, int opsVerificationSuccess);

    @Query("SELECT l FROM LeadDocuments l WHERE l.leadInformation = ?1")
    List<LeadDocuments> findByLeadId(LeadInformation leadInfo);
    @Query("SELECT l FROM LeadDocuments l WHERE l.leadInformation =?1 AND l.subDocumentType IN (?2) ORDER BY l.createdAt DESC")
    List<LeadDocuments> fetchLeadDocumentBySubDocumentType(LeadInformation leadInformation,List<SubDocumentType> documentTypeList);
    @Query("SELECT l FROM LeadDocuments l WHERE l.leadInformation =?1 AND l.subDocumentType =?2 ORDER BY l.createdAt DESC")
    List<LeadDocuments> getLeadDocumentsBySubDocType(LeadInformation leadInformation ,SubDocumentType subDocumentType);

//    @Query("select l from LeadDocuments where l.leadInformation.leadId=?1 and l.subDocumentType.subDocumentTypeId=?2 ORDER BY l.createdAt DESC")
//    List<LeadDocuments> getLeadDocumentsBySubDocType1(long leadInformation ,long subDocumentType);


    @Query("SELECT l.subDocumentType FROM LeadDocuments l WHERE l.leadInformation =?1 GROUP BY l.subDocumentType")
    List<SubDocumentType> fetchSubDocumentsByLeadId(LeadInformation leadInfo);

    @Query("select l from LeadDocuments l where l.leadInformation =?1 and l.documentMapping in (?2)")
    List<LeadDocuments> findLeadDocumentsByLeadAndDocumentList(LeadInformation leadInfo, List<DocumentMapping> mappingList);
    @Query("SELECT l.documentMapping FROM LeadDocuments l WHERE l.leadInformation =?1 GROUP BY l.documentMapping")
    List<DocumentMapping> fetchDocumentMappingByLeadId(LeadInformation leadInfo);

    @Query("SELECT l FROM LeadDocuments l WHERE l.leadInformation =?1 AND l.documentMapping =?2 ORDER BY l.createdAt DESC")
    List<LeadDocuments> getLeadDocumentsByDocumentMapping(LeadInformation leadInformation ,DocumentMapping documentMapping);

}
