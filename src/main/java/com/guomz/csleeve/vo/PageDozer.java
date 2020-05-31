package com.guomz.csleeve.vo;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * 同样属于分页类
 * 负责将model转化为vo并打包为分页对象返回，属于进一步的封装
 * 第一个泛型为model，第二个为vo
 */
@NoArgsConstructor
public class PageDozer<T, K> extends Paging{

    public PageDozer(Page<T> page, Class<K> kClass){
        this.initPaging(page);
        List<T> tList = page.getContent();
        List<K> kList = new ArrayList<>();
        //批量进行对象字段拷贝的工具类
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        tList.forEach(t -> {
            K vo = mapper.map(t, kClass);
            kList.add(vo);
        });
        this.setItems(kList);
    }
}
