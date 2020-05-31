package com.guomz.csleeve.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

/**
 * 宫格分类实体
 */
@Entity
@Getter
@Setter
@Table(name = "grid_category", schema = "missyou", catalog = "")
public class GridCategory extends BaseEntity{

    @Id
    private Long id;
    private String title;
    private String img;
    private String name;
    private Long categoryId;
    private Long rootCategoryId;
}
