package com.guomz.csleeve.service;

import com.guomz.csleeve.model.Spu;
import org.springframework.data.domain.Page;

public interface SpuService {

    Spu getDetail(Long id);

    Page<Spu> getLatestSpuByPage(int start, int count);

    Page<Spu> getSpuByCategory(long categoryId, boolean isRoot, int start, int count);
}
