package com.example.vkbot.service;

import com.example.vkbot.configuration.VkConfigurationApi;
import com.example.vkbot.dto.CallbackDTO;
import com.example.vkbot.dto.MessageSendDTO;
import com.example.vkbot.dto.RequestInfoDTO;
import com.example.vkbot.entity.MessageNewCallback;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Slf4j
public class CallbackService {
    private final VkConfigurationApi vkConfigurationApi;
    private final VkMessageSenderService vkMessageSenderService;

    public String handleCallback(CallbackDTO callbackDto) {
        validateSecret(callbackDto);

        switch (Objects.requireNonNull(callbackDto.type())) {
            case confirmation: {
                return vkConfigurationApi.getConfirmation();
            }
            case message_new: {
                parseAndSaveNewMessage(callbackDto);
                return "ok";
            }
            default: {
                throw new IllegalArgumentException("Service support only 'message_new' callback type");
            }
        }
    }

    private void validateSecret(CallbackDTO callbackDto) {
        if (!vkConfigurationApi.getSecret().equals(callbackDto.secret())) {
            throw new InvalidParameterException("Invalid secret");
        }
    }

    private void parseAndSaveNewMessage(CallbackDTO callbackDto) {
        RequestInfoDTO requestInfoDTO = callbackDto.requestInfoDTO();
        Map<String, Object> message = requestInfoDTO.message();

        MessageNewCallback messageForSave = MessageNewCallback.builder()
                .callbackType(callbackDto.type())
                .userId(Long.parseLong(String.valueOf(message.get("peer_id"))))
                .text(String.valueOf(message.get("text")))
                .build();

        log.info("The message was successfully parsed and saved: {}", messageForSave);
        handleMessageNew(messageForSave, message);
    }

    private void handleMessageNew(MessageNewCallback messageNewCallback, Map<String, Object> message) {
        Long peerId = Long.parseLong(String.valueOf(message.get("peer_id")));
        MessageSendDTO sendDto = MessageSendDTO.builder()
                .randomId(ThreadLocalRandom.current().nextInt())
                .peerId(peerId)
                .message("Вы сказали: ".concat(messageNewCallback.getText()))
                .build();
        log.info("Message ready to send");
        vkMessageSenderService.sendMessage(sendDto, peerId);
    }
}
