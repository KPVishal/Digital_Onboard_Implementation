package com.bijlipay.repository;

import com.bijlipay.model.FileTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FileTransferRepository extends JpaRepository<FileTransfer, Long> {
    @Query("select count(f) from FileTransfer f where f.mbNo=?1")
    int findByCount(long contactNumber);
    @Query("select count(f) from FileTransfer f where f.mailId=?1")
    int findByCount1(String contactMail);
}
