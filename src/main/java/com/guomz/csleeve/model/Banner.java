package com.guomz.csleeve.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "banner", schema = "missyou", catalog = "")
@Getter
@Setter
public class Banner extends BaseEntity {

    @Id
    private Long id;
    private String name;
    private String description;
    private String title;
    private String img;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "bannerId")
    private List<BannerItem> bannerItemList;
    @Override
    public String toString() {
        return "Banner{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", img='" + img + '\'' +
                '}';
    }

}
