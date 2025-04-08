package com.example.vkbot.service;

import com.example.vkbot.configuration.VkConfigurationApi;
import com.example.vkbot.dto.MessageSendDTO;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.security.InvalidParameterException;

import static com.example.vkbot.service.CallbackServiceTestConstant.CONFIRMATION;
import static com.example.vkbot.service.CallbackServiceTestConstant.INVALID_KEY;
import static com.example.vkbot.service.CallbackServiceTestConstant.INVALID_TYPE;
import static com.example.vkbot.service.CallbackServiceTestConstant.MESSAGE_NEW;
import static com.example.vkbot.service.CallbackServiceTestConstant.PEER_ID;
import static com.example.vkbot.service.CallbackServiceTestConstant.VALID_KEY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CallbackServiceTest {

    @InjectMocks
    private CallbackService callbackService;

    @Mock
    private VkConfigurationApi vkConfigurationApi;

    @Mock
    private VkMessageSenderService vkMessageSenderService;

    @Captor
    private ArgumentCaptor<MessageSendDTO> captorMessage;

    @Test
    void testHandleCallback_NotSecretKey() {
        returnKey(INVALID_KEY);

        assertThrows(InvalidParameterException.class, () -> callbackService.handleCallback(CONFIRMATION));
    }

    @Test
    void testHandleCallback_Confirmation() {
        returnKey(VALID_KEY);
        when(vkConfigurationApi.getConfirmation()).thenReturn("testConfirmation");

        String response = callbackService.handleCallback(CONFIRMATION);

        assertEquals("testConfirmation", response);
    }

    @Test
    void testHandleCallback_MessageNew() {
        returnKey(VALID_KEY);

        String response = callbackService.handleCallback(MESSAGE_NEW);

        verify(vkMessageSenderService, times(1)).sendMessage(captorMessage.capture(), anyLong());
        MessageSendDTO messageSendDTO = captorMessage.getValue();

        assertEquals("ok", response);
        assertEquals("Вы сказали: Hello", messageSendDTO.message());
    }

    @Test
    void testHandleCallback_InvalidType() {
        returnKey(VALID_KEY);

        assertThrows(IllegalArgumentException.class, () -> callbackService.handleCallback(INVALID_TYPE));
    }

    void returnKey(String key) {
        when(vkConfigurationApi.getSecret()).thenReturn(key);
    }
}