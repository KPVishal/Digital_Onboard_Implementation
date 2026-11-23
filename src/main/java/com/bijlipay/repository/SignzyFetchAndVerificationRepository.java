package com.bijlipay.repository;

import com.bijlipay.model.SignzyFetchAndVerification;
import com.bijlipay.model.UploadedDocuments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SignzyFetchAndVerificationRepository extends JpaRepository<SignzyFetchAndVerification,Long> {

    @Query("SELECT s FROM SignzyFetchAndVerification s WHERE s.uploadedDocuments IN ?1 AND s.panDetails IS NOT NULL")
    List<SignzyFetchAndVerification> fetchPanDetails(List<UploadedDocuments> uploadedDocuments);
}
