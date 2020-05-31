package com.guomz.csleeve.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 分页类基类，包含查询到的model集合
 * @param <T>
 */
@Getter
@Setter
@NoArgsConstructor
public class Paging<T> {

    private Long total;
    private Integer count;
    private Integer page;
    private Integer totalPage;
    private List<T> items;

    public Paging(Page<T> page){
        this.initPaging(page);
        this.items = page.getContent();
    }

    void initPaging(Page<T> page){
        this.total = page.getTotalElements();
        this.count = page.getSize();
        this.page = page.getNumber();
        this.totalPage = page.getTotalPages();
    }
}
