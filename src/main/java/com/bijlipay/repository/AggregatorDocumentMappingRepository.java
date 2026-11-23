package com.bijlipay.repository;

import com.bijlipay.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface AggregatorDocumentMappingRepository extends JpaRepository<AggregatorDocumentMapping, Long> {

    @Query("select ad from AggregatorDocumentMapping ad where ad.aggregator =?1")
    List<AggregatorDocumentMapping> findDocumentsByAggregator(Aggregator aggregator);

    @Transactional
    @Modifying
    @Query("delete from AggregatorDocumentMapping ad where ad.aggregator =?1")
    void deleteAllDocumentsByAggregator(Aggregator update);

    @Query("select ad.documentTypes from AggregatorDocumentMapping ad where ad.aggregator =?1")
    List<DocumentTypes> fetchDocumentTypeList(Aggregator aggregator);

    @Query("select ad from AggregatorDocumentMapping ad where ad.documentMapping.merchantType =?1 and ad.aggregator =?2")
    List<AggregatorDocumentMapping> findDocumentsByAggregatorWithType(MerchantType type, Aggregator aggregator);

    @Query("select ad.documentMapping.merchantDocumentType from AggregatorDocumentMapping ad where ad.documentMapping.merchantType =?2 and ad.aggregator =?1")
    List<MerchantDocumentType> findDocumentByMerchant(Aggregator aggregator1, MerchantType merchantType1);

    @Query("select ad from AggregatorDocumentMapping ad where ad.documentMapping.merchantType =?2 and ad.aggregator =?1 and ad.documentMapping.merchantDocumentType =?3")
    List<AggregatorDocumentMapping> findDocumentsByAggregatorWithTypeAndCategory(Aggregator aggregator1, MerchantType merchantType1, MerchantDocumentType merchantDocumentType);

    @Query("select ad.documentMapping from AggregatorDocumentMapping ad where ad.documentMapping.merchantType =?2 and ad.aggregator =?1 and ad.documentMapping.merchantDocumentType =?3")
    List<DocumentMapping> findDocumentsByMerchantTypeWithCategoryAndAggregator(Aggregator aggregator1, MerchantType merchantType1, MerchantDocumentType merchantDocumentType);

    @Query("select ad.documentMapping from AggregatorDocumentMapping ad where ad.documentMapping.merchantType =?1 and ad.aggregator =?2")
    List<DocumentMapping> findDocumentsByAggregatorWithMerchantType(MerchantType merchantType1, Aggregator aggregator1);

    @Query("select ad.merchantDocumentType from AggregatorDocumentMapping ad where ad.aggregator =?1 and ad.merchantType =?2 group by ad.merchantDocumentType")
    List<MerchantDocumentType> findDocumentMappingByAggregator(Aggregator aggregator, MerchantType merchantType);

    @Query("select ad.documentMapping from AggregatorDocumentMapping ad where ad.merchantDocumentType =?1")
    List<DocumentMapping> findDocumentsByDocumentType(MerchantDocumentType documentType);
}
