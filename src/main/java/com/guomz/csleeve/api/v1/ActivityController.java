package com.guomz.csleeve.api.v1;

import com.guomz.csleeve.exception.http.NotFoundException;
import com.guomz.csleeve.model.Activity;
import com.guomz.csleeve.service.ActivityServiceImpl;
import com.guomz.csleeve.vo.ActivityPureVo;
import com.guomz.csleeve.vo.ActivityWithCouponPureVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/activity")
public class ActivityController {

    @Autowired
    private ActivityServiceImpl activityService;

    /**
     * 根据名称获取主题
     * @param name
     * @return
     */
    @GetMapping("/name/{name}")
    public ActivityPureVo getByName(@PathVariable("name") String name){
        Activity activity = activityService.getByName(name);
        if(activity == null){
            throw new NotFoundException(40001);
        }
        return new ActivityPureVo(activity);
    }

    /**
     * 根据名称获取主题和其关联的优惠券信息，优惠券信息不携带关联的品类信息
     * @param name
     * @return
     */
    @GetMapping("/name/{name}/with_coupon")
    public ActivityWithCouponPureVo getByNameWithCoupon(@PathVariable("name") String name){
        Activity activity = activityService.getByName(name);
        if(activity == null){
            throw new NotFoundException(40001);
        }
        return new ActivityWithCouponPureVo(activity);
    }
}
