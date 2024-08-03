package com.fawryEx.storyEx.repository;

import com.fawryEx.storyEx.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByStoreIdAndProductId(Long storeId, Long productId);
    List<Stock> findByStoreIdInAndProductIdIn(List<Long> storeIds, List<Long> productIds);
}
