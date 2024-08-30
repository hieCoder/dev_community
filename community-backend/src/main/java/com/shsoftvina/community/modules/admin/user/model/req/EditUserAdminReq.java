package com.shsoftvina.community.modules.admin.user.model.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shsoftvina.community.utils.JsonUtils;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class EditUserAdminReq {

    @NotBlank(message = "username is not blank")
    private String username;
    private String firstName;
    private String lastName;
    @Email
    private String email;
    private String phone;
    @JsonIgnore
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
    public void setSkillsObject(Object skillsObject){
        this.skills = JsonUtils.objectToJson(skillsObject);
    }

    @JsonProperty("avatar")
    public void setAvatarObject(Object avatarObject) {
        this.avatar = JsonUtils.objectToJson(avatarObject);
    }
}
