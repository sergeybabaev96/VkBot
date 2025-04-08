package com.example.vkbot.configuration;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@PropertySource(value = "classpath:vk.properties")
@ConfigurationProperties(prefix = "vk.api")
public class VkConfigurationApi {

    @NotBlank
    private String accessToken;

    @NotBlank
    private Double v;

    @NotBlank
    private String secret;

    @NotBlank
    private String confirmation;

    private int maxSizeMessage;
}
