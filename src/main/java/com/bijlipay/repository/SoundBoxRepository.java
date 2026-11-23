package com.bijlipay.repository;

import com.bijlipay.model.SoundBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SoundBoxRepository extends JpaRepository<SoundBox,Long> {
    @Query("select m from SoundBox m where m.id =?1")
    SoundBox findById1(long soundBoxId);
}
