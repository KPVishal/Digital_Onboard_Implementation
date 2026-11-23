package com.bijlipay.repository;

import com.bijlipay.model.Document;
import com.bijlipay.model.MerchantDocumentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {


    @Query("select d from Document d where d.name =?1 and d.documentType =?2")
    Document findDocument(String name, MerchantDocumentType documentType);

    @Query("select d from Document d where d.isActive =?1 and d.documentType =?2 and (d.name like %?3% OR d.documentType like %?3%)")
    Page<Document> findDocumentByDocumentTypeWithSearchTerm(boolean active, MerchantDocumentType documentType1, String searchTerm, Pageable pageable);

    @Query("select d from Document d where d.isActive =?1 and d.documentType =?2")
    Page<Document> findDocumentByDocumentTypeWithOutSearchTerm(boolean active, MerchantDocumentType documentType1, Pageable pageable);

    @Query("select d from Document d where d.id =?1")
    Document fetchDocumentById(long id);
}
