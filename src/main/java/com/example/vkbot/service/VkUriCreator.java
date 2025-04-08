package com.example.vkbot.service;

import com.example.vkbot.exception.MessageSenderException;
import com.example.vkbot.configuration.VkConfigurationApi;
import com.example.vkbot.dto.MessageSendDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class VkUriCreator {
    private final VkConfigurationApi vkConfigurationApi;
    private final ObjectMapper objectMapper;

    public URI createUri(MessageSendDTO dto) {
        try {
            MultiValueMap map = objectMapper.convertValue(dto, LinkedMultiValueMap.class);
            return UriComponentsBuilder.fromUriString("https://api.vk.com/method/messages.send")
                    .queryParam("access_token", vkConfigurationApi.getAccessToken())
                    .queryParam("v", vkConfigurationApi.getV())
                    .queryParams(map)
                    .build()
                    .toUri();
        } catch (ClassCastException e) {
            throw new MessageSenderException(e.getMessage());
        }
    }
}
