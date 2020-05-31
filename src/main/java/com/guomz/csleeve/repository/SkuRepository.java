package com.guomz.csleeve.repository;

import com.guomz.csleeve.model.Sku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkuRepository extends JpaRepository<Sku, Long> {

    List<Sku> findAllByIdIn(List<Long> idList);

    @Modifying
    @Query("update Sku s\n" +
            "set s.stock = s.stock - :count\n" +
            "where\n" +
            "s.id = :skuId and\n" +
            "s.stock >= :count")
    int reduceStockById(Long skuId, Integer count);
}
