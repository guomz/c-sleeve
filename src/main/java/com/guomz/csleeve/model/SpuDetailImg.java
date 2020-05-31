package com.guomz.csleeve.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "spu_detail_img", schema = "missyou", catalog = "")
@Getter
@Setter
public class SpuDetailImg extends BaseEntity{
    @Id
    private Long id;
    private String img;
    private Long spuId;
    private int index;


}
