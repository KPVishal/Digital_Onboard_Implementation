package com.bijlipay.repository;

import com.bijlipay.model.MerchantCategoryCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantCategoryCodeRepository extends JpaRepository<MerchantCategoryCode, Long> {
    @Query("SELECT m FROM MerchantCategoryCode m WHERE m.mccCode=?1")
    MerchantCategoryCode findByCode(String mccCode);
}
