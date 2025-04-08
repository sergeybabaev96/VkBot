package com.example.vkbot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.Map;

@Builder
public record RequestInfoDTO(
        @JsonProperty(value = "client_info")
        ClientInfoDTO clientInfoDTO,
        Map<String, Object> message
) {
}
