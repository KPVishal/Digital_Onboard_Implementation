package com.bijlipay.repository;

import com.bijlipay.model.InternalSubvention;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InternalSubventionRepository extends JpaRepository<InternalSubvention,Long> {

    @Query("select m from InternalSubvention m where m.leadId=?1 and m.status<>3")
    InternalSubvention getDetailByLeadId(long leadId);

    @Query("select m from InternalSubvention m where m.leadId=?1 and m.status=2 ORDER BY m.createdAt DESC")
    List<InternalSubvention> getDetailByLeadIds(long leadId);
    @Query("select m from InternalSubvention m where m.leadId=?1  ORDER BY m.createdAt DESC")
    List<InternalSubvention> DetailByLeadIds(long leadId);
}
