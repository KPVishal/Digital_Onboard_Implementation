package com.bijlipay.repository;

import com.bijlipay.model.Kyc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KycRepository extends JpaRepository<Kyc, Long> {

    @Query("select k from Kyc k where k.name =?1")
    Kyc findKyc(String name);


    @Query("select k from Kyc k where k.id = ?1")
    Kyc findKycById(long id);

    @Query("select k from Kyc k where k.isActive =?1")
    List<Kyc> findActiveKyc(boolean active);
}
