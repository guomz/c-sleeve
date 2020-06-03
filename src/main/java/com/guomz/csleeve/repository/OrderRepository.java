package com.guomz.csleeve.repository;

import com.guomz.csleeve.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findByUserIdAndExpiredTimeGreaterThanAndStatus(Long userId, Date nowTime, Integer status, Pageable pageable);

    Page<Order> findByUserId(Long userId, Pageable pageable);

    Page<Order> findByUserIdAndStatus(Long userId, Integer status, Pageable pageable);

    Order findByUserIdAndId(Long userId, Long id);

    @Modifying
    @Query("update Order o\n" +
            "set o.status = :status\n" +
            "where\n" +
            "o.id = :orderId and\n" +
            "o.status = 1")
    Integer cancelOrder(Long orderId, Integer status);
}

