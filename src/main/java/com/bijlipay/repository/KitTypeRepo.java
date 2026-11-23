package com.bijlipay.repository;

import com.bijlipay.model.KitType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KitTypeRepo extends JpaRepository<KitType,Long> {
    @Query("SELECT k FROM KitType k")
    List<KitType> getKitType();

    @Query("SELECT k FROM KitType k where k.id=?1")
    KitType find(Long kit);
}
