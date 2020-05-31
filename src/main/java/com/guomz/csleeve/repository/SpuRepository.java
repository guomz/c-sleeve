package com.guomz.csleeve.repository;

import com.guomz.csleeve.model.Spu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpuRepository extends JpaRepository<Spu, Long> {

    Spu findSpuById(Long id);

    Page<Spu> findSpuByCategoryId(Long categoryId, Pageable pageable);

    Page<Spu> findSpuByRootCategoryId(Long categoryId, Pageable pageable);
}
