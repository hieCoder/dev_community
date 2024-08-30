package com.shsoftvina.community.modules.admin.notification.model.res;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shsoftvina.community.domain.enumration.EActionNotification;
import com.shsoftvina.community.utils.JsonUtils;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Map;

@Setter
@Getter
public class NotificationAdminRes {

    private Long id;
    private String href;
    private Instant createdDate;
    private EActionNotification action;
    private boolean isRead;
    @JsonIgnore
    private String metadata;

    @JsonProperty("message")
    public String getMessage(){
        return action.getMessage();
    }

    @JsonProperty("metadata")
    public Object getMetadataObject(){
        return JsonUtils.jsonToObject(metadata, Object.class);
    }
}
