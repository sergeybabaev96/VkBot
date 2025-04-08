package com.example.vkbot.service;

import com.example.vkbot.dto.CallbackDTO;
import com.example.vkbot.dto.RequestInfoDTO;
import com.example.vkbot.enums.CallbackType;

import java.util.Map;

public class CallbackServiceTestConstant {
    protected static final String INVALID_KEY = "333";
    protected static final String VALID_KEY = "111";
    protected static final Long PEER_ID = 123L;
    protected static final Map<String, Object> MESSAGE = Map.of("peer_id", PEER_ID, "text", "Hello");
    protected static final RequestInfoDTO REQUEST_INFO = RequestInfoDTO.builder()
            .message(MESSAGE)
            .build();
    protected static final CallbackDTO CONFIRMATION = CallbackDTO.builder()
            .type(CallbackType.confirmation)
            .secret(VALID_KEY)
            .build();
    public static final CallbackDTO MESSAGE_NEW = CallbackDTO.builder()
            .type(CallbackType.message_new)
            .secret(VALID_KEY)
            .requestInfoDTO(REQUEST_INFO)
            .build();
    protected static final CallbackDTO INVALID_TYPE = CallbackDTO.builder()
            .type(CallbackType.invalid)
            .secret(VALID_KEY)
            .build();
}
