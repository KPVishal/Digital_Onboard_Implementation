package com.bijlipay.repository;

import com.bijlipay.model.Device;
import com.bijlipay.model.MarsRentalPlans;
import com.bijlipay.model.Plan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarsRentalPlansRepository extends JpaRepository<MarsRentalPlans, Long> {
    @Query("SELECT COUNT(m) FROM MarsRentalPlans m WHERE m.name =?1")
    int findCountByName(String name);

    @Query("SELECT m FROM MarsRentalPlans m WHERE m.code LIKE ?1% OR m.name LIKE ?1%")
    Page<MarsRentalPlans> fetchAllRentalListWithSearchTerm(String searchTerm, Pageable pageable);

    @Query("SELECT m FROM MarsRentalPlans m")
    Page<MarsRentalPlans> fetchAllRentalListWithoutSearchTerm(Pageable pageable);

    @Query("SELECT p.plan FROM MarsRentalPlans p WHERE p.leadSource=?1")
    List<Plan> getPlanList(int sourceId);

    @Query("SELECT p.device FROM MarsRentalPlans p WHERE p.leadSource =?1")
    List<Device> getDeviceList(int sourceId);


    @Query("SELECT p FROM MarsRentalPlans p WHERE p.id=?1")
    MarsRentalPlans find(long ids);

    @Query("SELECT p.plan FROM MarsRentalPlans p WHERE p.plan.id=?1 and p.device.id=?2 and p.leadSource.id=?3")
    Plan getPlanById(long ids, long i, int leadSource);

    @Query("SELECT p FROM MarsRentalPlans p WHERE p.plan.id=?1 and p.device.id=?2 and p.leadSource.id=?3")
    MarsRentalPlans getPlanByIds(long ids, long i, int leadSource);
}
