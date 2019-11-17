package com.yourrights.beans;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "config")
@Getter
@Setter
public class ConfigProperties {

    private Integer numMaxPage;
}
