package com.bijlipay.repository;

import com.bijlipay.model.DocumentCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentCategoryRepository extends JpaRepository<DocumentCategory,Long> {
    @Query("SELECT d FROM DocumentCategory d WHERE d.isActive =?1")
    List<DocumentCategory> findAllCategoryList(boolean isActive);
}
