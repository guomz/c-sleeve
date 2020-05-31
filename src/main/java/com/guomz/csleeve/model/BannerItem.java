package com.guomz.csleeve.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "banner_item", schema = "missyou", catalog = "")
@Getter
@Setter
public class BannerItem extends BaseEntity {
    @Id
    private Long id;
    private String img;
    private String keyword;
    private short type;
    private Long bannerId;
    private String name;

    @Override
    public String toString() {
        return "BannerItem{" +
                "id=" + id +
                ", img='" + img + '\'' +
                ", keyword='" + keyword + '\'' +
                ", type=" + type +
                ", bannerId=" + bannerId +
                ", name='" + name + '\'' +
                '}';
    }
}
