package com.example.vkbot.service;

import com.example.vkbot.dto.MessageSendErrorResultDTO;
import com.example.vkbot.dto.MessageSendDTO;
import com.example.vkbot.dto.MessageSendResultDTO;

import java.net.URI;

public class VkMessageSenderServiceTestConstant {
    protected static final Long PEER_ID = 12345L;
    protected static final int RANDOM_ID = 67890;
    protected static final int MAX_LENGTH_MESSAGE = 100;
    protected static final String MESSAGE1 = "Привет";
    protected static final String MESSAGE2 = "a".repeat(101);
    protected static final URI FAKE_URI = URI.create("https://vk.com/fake");
    protected static final MessageSendDTO SHORT_MESSAGE = MessageSendDTO.builder()
            .message(MESSAGE1)
            .build();
    protected static final MessageSendDTO LONG_MESSAGE = MessageSendDTO.builder()
            .message(MESSAGE2)
            .peerId(PEER_ID)
            .randomId(RANDOM_ID)
            .build();
    protected static final MessageSendErrorResultDTO ERROR = new MessageSendErrorResultDTO(100, "Invalid peer_id");
    protected static final MessageSendResultDTO MESSAGE_ERROR_RESULT = MessageSendResultDTO.builder()
            .error(ERROR)
            .build();
}
