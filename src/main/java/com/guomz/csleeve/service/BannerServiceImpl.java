package com.guomz.csleeve.service;

import com.guomz.csleeve.model.Banner;
import com.guomz.csleeve.repository.BannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BannerServiceImpl implements BannerService{

    @Autowired
    private BannerRepository bannerRepository;

    public Banner getById(Long id){
        return bannerRepository.findBannerById(id);
    }

    public Banner getByName(String name){
        return bannerRepository.findBannerByName(name);
    }
}
