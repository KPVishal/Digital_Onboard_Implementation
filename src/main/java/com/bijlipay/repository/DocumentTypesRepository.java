package com.bijlipay.repository;

import com.bijlipay.model.DocumentTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentTypesRepository extends JpaRepository<DocumentTypes, Long> {

    @Query("select d from DocumentTypes d where d.document =?1")
    DocumentTypes checkDocumentType(String document);

    @Query("select d from DocumentTypes d where d.id =?1")
    DocumentTypes findDocumentById(long id);
}
