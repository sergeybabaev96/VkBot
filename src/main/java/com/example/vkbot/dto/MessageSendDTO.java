package com.example.vkbot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;


@Builder
@JsonPropertyOrder(alphabetic = true)
public record MessageSendDTO(@JsonProperty(value = "user_id") Long userId,
                             @JsonProperty(value = "peer_id") Long peerId,
                             @JsonProperty(value = "random_id") Integer randomId,
                             String message) {
}
