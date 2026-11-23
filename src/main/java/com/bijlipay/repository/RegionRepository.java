package com.bijlipay.repository;

import com.bijlipay.model.Region;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RegionRepository extends JpaRepository<Region,Integer> {

    @Query("select r from Region r where regionAreaName=?1")
    Region getRegion(String cell);

    @Query("select r from Region r where regionCode=?1")
    Region getRegionByCode(String cell);
}
