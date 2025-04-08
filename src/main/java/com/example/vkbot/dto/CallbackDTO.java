package com.example.vkbot.dto;

import com.example.vkbot.enums.CallbackType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record CallbackDTO(

        @JsonProperty(value = "group_id")
        Long groupId,
        CallbackType type,

        @JsonProperty(value = "event_id")
        String eventId,

        @JsonProperty(value = "object")
        RequestInfoDTO requestInfoDTO,
        String secret
) {
}

