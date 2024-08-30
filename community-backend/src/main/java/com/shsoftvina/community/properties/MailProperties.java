package com.shsoftvina.community.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "community.mail", ignoreUnknownFields = false)
@Data
public class MailProperties {
    private boolean enabled = false;
    private String from = "";
}