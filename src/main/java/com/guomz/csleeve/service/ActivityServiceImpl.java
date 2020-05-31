package com.guomz.csleeve.service;

import com.guomz.csleeve.model.Activity;
import com.guomz.csleeve.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityServiceImpl {

    @Autowired
    private ActivityRepository activityRepository;

    /**
     * 根据名称查找主题
     * @param name
     * @return
     */
    public Activity getByName(String name){
        Activity activity = activityRepository.findActivityByName(name);
        return activity;
    }
}
