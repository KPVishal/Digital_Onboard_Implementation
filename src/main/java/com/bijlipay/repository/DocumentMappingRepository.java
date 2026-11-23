package com.bijlipay.repository;

import com.bijlipay.model.Document;
import com.bijlipay.model.DocumentMapping;
import com.bijlipay.model.MerchantDocumentType;
import com.bijlipay.model.MerchantType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentMappingRepository extends JpaRepository<DocumentMapping, Long> {


    @Query("select d from DocumentMapping d where d.documentName =?1 and d.merchantType =?2 and d.merchantDocumentType =?3 and d.document =?4")
    DocumentMapping findMapping(String documentName, MerchantType merchantType, MerchantDocumentType merchantDocumentType, Document document);

    @Query("select d from DocumentMapping d where d.isActive =?1 and (d.merchantType.merchantTypeName like %?2% OR d.merchantDocumentType.documentType like %?2% OR d.documentName like %?2%)")
    Page<DocumentMapping> findDocumentMappingWithSearchTerm(boolean active, String searchTerm, Pageable pageable);

    @Query("select d from DocumentMapping d where d.isActive =?1")
    Page<DocumentMapping> findDocumentMappingWithOutSearchTerm(boolean active, Pageable pageable);

    @Query("select d from DocumentMapping d where d.id =?1")
    DocumentMapping findDocumentMappingById(long id);

    @Query("select d from DocumentMapping d where d.merchantType =?1")
    List<DocumentMapping> findDocumentsByMerchantType(MerchantType type);

    @Query("select d from DocumentMapping d where d.merchantType =?1")
    List<DocumentMapping> findMerchantDocumentsByMerchantType(MerchantType merchant);

    @Query("select d.merchantDocumentType from DocumentMapping d where d.merchantType =?1")
    List<MerchantDocumentType> findTypesByMerchantTypes(MerchantType merchantType1);

    @Query("select d from DocumentMapping d where d.merchantType =?1 and d.merchantDocumentType =?2")
    List<DocumentMapping> findDocumentsByMerchantTypeWithCategory(MerchantType merchantType1, MerchantDocumentType merchantDocumentType);

    @Query("select d from DocumentMapping d where d.merchantDocumentType =?1")
    List<DocumentMapping> findDocumentsByMerchantDocumentType(MerchantDocumentType documentType);

    @Query("SELECT d FROM DocumentMapping d WHERE d.documentName =?1")
    DocumentMapping findMappingName(String subDocumentType);
}
