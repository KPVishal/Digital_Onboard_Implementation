package com.bijlipay.repository;

import com.bijlipay.model.Agreement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgreementRepository extends JpaRepository<Agreement, Long> {


    @Query("select a from Agreement a where a.name =?1")
    Agreement findAgreementName(String agreement);

    @Query("select a from Agreement a where a.id =?1")
    Agreement findAgreementById(long id);

    @Query("select a from Agreement a where a.isActive =?1")
    List<Agreement> findActiveAgreement(boolean active);
}
