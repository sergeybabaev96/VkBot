package com.example.vkbot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record MessageSendResultDTO(@JsonProperty(value = "peer_id") Long peerId,
                                   @JsonProperty(value = "message_id") Long messageId,
                                   @JsonProperty(value = "error") MessageSendErrorResultDTO error
                                    ) {
}
