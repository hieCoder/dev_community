package com.shsoftvina.community.modules.admin.user.model.res;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shsoftvina.community.utils.JsonUtils;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserAdminRes {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String avatar;
    private String country;
    private String title;
    private String school;
    private String degree;
    private LocalDate startFrom;
    private LocalDate endingIn;
    @JsonIgnore
    private String skills;

    @JsonProperty("skills")
    public Object getSkillsObject(){
        return JsonUtils.jsonToObject(this.skills, Object.class);
    }

    @JsonProperty("avatar")
    private Object getCoverObject() { return JsonUtils.jsonToObject(this.avatar, Object.class); }
}
