package com.bijlipay.repository;

import com.bijlipay.model.SettlementTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SettlementTypesRepository extends JpaRepository<SettlementTypes, Integer> {

    @Query("select s from SettlementTypes s where s.settlement =?1")
    SettlementTypes checkSettleConfig(String settlement);

    @Query("select s from SettlementTypes s where s.id =?1")
    SettlementTypes findSettlementById(int id);
}
