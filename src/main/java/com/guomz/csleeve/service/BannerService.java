package com.guomz.csleeve.service;

import com.guomz.csleeve.model.Banner;

public interface BannerService {

    Banner getByName(String name);
    Banner getById(Long id);
}
