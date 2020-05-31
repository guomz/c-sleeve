package com.guomz.csleeve.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.guomz.csleeve.utils.JsonConverterVer2;
import com.sun.scenario.effect.Identity;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String openid;
    private String nickname;
    private Long unifyUid;
    private String email;
    private String password;
    private String mobile;
    //存放微信返回的用户信息，比如openid等等
    private String wxProfile;

    public Map<String, Object> getWxProfile() {
        return JsonConverterVer2.jsonToObejct(this.wxProfile, new TypeReference<Map<String, Object>>() {});
    }

    public void setWxProfile(Map<String, Object> wxProfileMap) {
        this.wxProfile = JsonConverterVer2.objectToJson(wxProfileMap);
    }
}
