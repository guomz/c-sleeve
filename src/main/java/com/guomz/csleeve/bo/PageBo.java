package com.guomz.csleeve.bo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 承载页号与每页的数据数目
 */
@Getter
@Setter
@NoArgsConstructor
public class PageBo {

    private Integer pageNum;
    private Integer count;

    public PageBo(int pageNum, int count){
        this.count = count;
        this.pageNum = pageNum;
    }
}
