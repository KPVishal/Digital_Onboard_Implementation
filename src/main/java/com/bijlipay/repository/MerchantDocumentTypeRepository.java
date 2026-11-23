package com.bijlipay.repository;

import com.bijlipay.model.MerchantDocumentType;
import com.bijlipay.model.MerchantType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantDocumentTypeRepository extends JpaRepository<MerchantDocumentType, Long> {

    @Query("select m from MerchantDocumentType m where m.documentType =?1 and m.merchantType =?2")
    MerchantDocumentType findDocumentType(String documentType, MerchantType merchantType);

    @Query("select m from MerchantDocumentType m where m.isActive =?1 and m.merchantType =?2 and (m.documentType like %?3% OR m.categoryDescription like %?3% OR m.uploadAnyOne like %?3%)")
    Page<MerchantDocumentType> findDocumentTypeWithSearchTerm(boolean active, MerchantType type, String searchTerm, Pageable pageable);

    @Query("select m from MerchantDocumentType m where m.isActive =?1 and m.merchantType =?2")
    Page<MerchantDocumentType> findDocumentTypeWithOutSearchTerm(boolean active, MerchantType type, Pageable pageable);

    @Query("select m from MerchantDocumentType m where m.id =?1")
    MerchantDocumentType findDocumentTypeById(long id);

}
