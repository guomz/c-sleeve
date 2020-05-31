package com.guomz.csleeve.repository;

import com.guomz.csleeve.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    Activity findActivityByName(String name);

    Optional<Activity> findFirstByCouponList(Long couponId);
}
