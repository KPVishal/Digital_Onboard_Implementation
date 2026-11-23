package com.bijlipay.repository;

import com.bijlipay.model.RiskMccNpci;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RiskMccNpciRepo extends JpaRepository<RiskMccNpci,Integer> {
    @Query("select m.mcc.mccCode from RiskMccNpci m")
    List<String> find();
}
