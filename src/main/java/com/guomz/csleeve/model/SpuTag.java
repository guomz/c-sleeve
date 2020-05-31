package com.guomz.csleeve.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "spu_tag", schema = "missyou", catalog = "")
@Getter
@Setter
public class SpuTag extends BaseEntity{
    @Id
    private Long id;
    private Long spuId;
    private Long tagId;


}
