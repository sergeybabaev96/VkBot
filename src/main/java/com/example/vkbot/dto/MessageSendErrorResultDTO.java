package com.example.vkbot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record MessageSendErrorResultDTO(
        @JsonProperty(value = "error_code")
        Integer errorCode,
        @JsonProperty(value = "error_msg")
        String errorMsg
) {
}
