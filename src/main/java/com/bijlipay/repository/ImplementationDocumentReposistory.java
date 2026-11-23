package com.bijlipay.repository;

import com.bijlipay.model.ImplementationDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImplementationDocumentReposistory extends JpaRepository<ImplementationDocument,Long> {
    @Query("select i from ImplementationDocument i where i.leadId=?1")
    ImplementationDocument getAllByLeads(long leadId);
}
