package com.example.vkbot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ClientInfoDTO(
        List<String> buttonAction,
        boolean keyboard,
        boolean inlineKeyboard,
        boolean carousel,
        int langId
) {
}
