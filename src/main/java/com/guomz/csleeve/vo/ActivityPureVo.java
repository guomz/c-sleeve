package com.guomz.csleeve.vo;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.guomz.csleeve.model.Activity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
public class ActivityPureVo {

    private Long id;
    private String title;
    private String description;
    private Date startTime;
    private Date endTime;
    private String remark;
    private Boolean online;
    private String entranceImg;
    private String internalTopImg;
    private String name;

    public ActivityPureVo(Activity activity){
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        BeanUtils.copyProperties(activity, this);
    }
}
