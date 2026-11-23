package com.bijlipay.repository;

import com.bijlipay.model.LocationData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface LocationDataRepository extends JpaRepository<LocationData,Long> {

    @Query(value = "select new map(l.stateName AS stateName,l.state AS stateId) from LocationData l group by l.stateName,l.state")
    List<HashMap<String, Object>> getmerchantdataStatename1();

    @Query(value = "select new map(l.districtName AS districtName, l.district AS districtId) from LocationData l where l.stateName = ?1 group by l.districtName,l.district")
    List<HashMap<String, Object>> getmerchantdataDistrictname1(String s);

    @Query(value = "select new map(m.subDistrictName AS subDistrictName,m.subDistrict AS subDistrictId) from LocationData m where m.stateName = ?1 AND m.districtName = ?2 group by m.subDistrictName,m.subDistrict")
    List<HashMap<String, Object>> getmerchantdataSubDistrictname1(String s, String s1);

    @Query(value = "select new map(m.townVillageName AS townVillageName,m.townVillage AS townVillageId) from LocationData m where m.stateName = ?1 AND m.districtName = ?2 AND m.subDistrictName = ?3 group by m.townVillageName,m.townVillage")
    List<HashMap<String, Object>> getmerchantdataTownVillagename1(String statename,String districtname,String subDistrictname);

    @Query(value = "select (m.tier) from census_data m where m.state_name = ?1 AND m.district_name = ?2 AND m.sub_district_name = ?3 AND m.town_village_name= ?4 order by m.tier limit 1",nativeQuery = true)
    String getmerchantdataTiername1(String statename,String districtname,String subDistrictname,String villagename);
}
