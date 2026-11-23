package com.bijlipay.repository;

import com.bijlipay.model.TidVpaMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TidVpaMappingRepository extends JpaRepository<TidVpaMapping, Long>{
    @Query("select t from TidVpaMapping t where t.vpa =?1 and t.marsDeviceDetails.tid =?2")
    TidVpaMapping findVpa(String vpa, String tid);


}
