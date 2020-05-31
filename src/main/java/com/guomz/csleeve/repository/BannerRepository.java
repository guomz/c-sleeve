package com.guomz.csleeve.repository;

import com.guomz.csleeve.model.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {

    Banner findBannerById(Long id);

    Banner findBannerByName(String name);

}
