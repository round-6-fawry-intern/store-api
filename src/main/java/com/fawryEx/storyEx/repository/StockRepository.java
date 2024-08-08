package com.fawryEx.storyEx.repository;

import com.fawryEx.storyEx.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
  //    Optional<Stock> findByStoreIdAndProductId(Long storeId, Long productId);



  @Query("SELECT s FROM Stock s WHERE s.storeId = :storeId AND s.productId = :productId")
  Optional<Stock> findByStoreIdAndProductId(
      @Param("storeId") Long storeId, @Param("productId") Long productId);




  //    List<Stock> findByStoreIdInAndProductIdIn(List<Long> storeIds, List<Long> productIds);
  @Query("SELECT s FROM Stock s WHERE s.storeId IN :storeIds AND s.productId IN :productIds")
  List<Stock> findByStoreIdInAndProductIdIn(
      @Param("storeIds") List<Long> storeIds, @Param("productIds") List<Long> productIds);

  List<Stock> findByStoreId(Long storeId);

  List<Stock> findByProductId(Long productId);
}
