package com.bijlipay.repository;

import com.bijlipay.model.DocumentCategory;
import com.bijlipay.model.SubDocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubDocumentTypeRepository extends JpaRepository<SubDocumentType,Long> {
    @Query("SELECT s FROM SubDocumentType s WHERE s.documentCategory =?1 AND s.isActive =?2")
    List<SubDocumentType> findByDocumentCategory(DocumentCategory documentCategory, boolean isActive);
}
