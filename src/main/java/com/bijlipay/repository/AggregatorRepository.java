package com.bijlipay.repository;

import com.bijlipay.model.Aggregator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AggregatorRepository extends JpaRepository<Aggregator, Long> {


    @Query("select a from Aggregator a where a.aggregator =?1")
    Aggregator findAggregator(String aggregatorName);

    @Query("select a from Aggregator a where a.id =?1")
    Aggregator findAggregatorById(long id);

    @Query("select a from Aggregator a")
    List<Aggregator> findAggregators();

    @Query("select a from Aggregator a where a.id = ?1")
    Aggregator findAggregatorWithId(Optional<Long> aggregator);
}
