package com.example.vkbot.service;

import com.example.vkbot.configuration.VkConfigurationApi;
import com.example.vkbot.dto.MessageSendErrorResultDTO;
import com.example.vkbot.dto.MessageSendResultDTO;
import com.example.vkbot.dto.MessageSendDTO;
import com.example.vkbot.exception.MessageSenderException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Slf4j
public class VkMessageSenderService {
    private final RestTemplate restTemplate;
    private final VkUriCreator vkUriCreator;
    private final VkConfigurationApi vkConfigurationApi;

    public void sendMessage(MessageSendDTO messageSendDTO, Long peerId) {
        if (messageSendDTO.message().length() > vkConfigurationApi.getMaxSizeMessage()) {
            MessageSendDTO messagesSend = MessageSendDTO.builder()
                    .randomId(ThreadLocalRandom.current().nextInt())
                    .peerId(peerId)
                    .message(String.format("Сообщение не может быть длиннее %d символов",
                            vkConfigurationApi.getMaxSizeMessage()))
                    .build();
            sendInternal(messagesSend);
            return;
        }
        sendInternal(messageSendDTO);
    }

    public void sendInternal(MessageSendDTO message) {
        URI uri = vkUriCreator.createUri(message);
        ResponseEntity<MessageSendResultDTO> responseEntity = restTemplate
                .postForEntity(uri, null, MessageSendResultDTO.class);
        log.info("Message was successfully send: {}", message);
        log.info("Response VK API: {}", responseEntity);
        validateResponse(responseEntity);
    }

    private void validateResponse(ResponseEntity<MessageSendResultDTO> responseEntity) {
        MessageSendErrorResultDTO error = Objects.requireNonNull(responseEntity.getBody()).error();
        if (error != null && error.errorCode() != null) {
            throw new MessageSenderException(error.errorMsg());
        }
    }
}
