package com.example.vkbot.service;

import com.example.vkbot.configuration.VkConfigurationApi;
import com.example.vkbot.dto.MessageSendDTO;
import com.example.vkbot.dto.MessageSendResultDTO;
import com.example.vkbot.exception.MessageSenderException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static com.example.vkbot.service.VkMessageSenderServiceTestConstant.MESSAGE_ERROR_RESULT;
import static com.example.vkbot.service.VkMessageSenderServiceTestConstant.FAKE_URI;
import static com.example.vkbot.service.VkMessageSenderServiceTestConstant.LONG_MESSAGE;
import static com.example.vkbot.service.VkMessageSenderServiceTestConstant.MAX_LENGTH_MESSAGE;
import static com.example.vkbot.service.VkMessageSenderServiceTestConstant.PEER_ID;
import static com.example.vkbot.service.VkMessageSenderServiceTestConstant.SHORT_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VkMessageSenderServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private VkUriCreator vkUriCreator;

    @Mock
    private VkConfigurationApi vkConfigurationApi;

    @InjectMocks
    private VkMessageSenderService vkMessageSenderService;

    @Captor
    private ArgumentCaptor<MessageSendDTO> dtoCaptor;

    @Test
    void shouldSendOriginalMessage_WhenMessageLengthIsWithinLimit() {
        returnMaxLengthMessage();
        returnFakeUri();
        when(restTemplate.postForEntity(eq(FAKE_URI), isNull(), eq(MessageSendResultDTO.class)))
                .thenReturn(ResponseEntity.ok(MessageSendResultDTO.builder().build()));

        vkMessageSenderService.sendMessage(LONG_MESSAGE, PEER_ID);

        verify(restTemplate).postForEntity(eq(FAKE_URI), isNull(), eq(MessageSendResultDTO.class));
    }

    @Test
    void shouldSendErrorMessage_WhenMessageTooLong() {
        returnMaxLengthMessage();
        returnFakeUri();
        when(restTemplate.postForEntity(eq(FAKE_URI), isNull(), eq(MessageSendResultDTO.class)))
                .thenReturn(ResponseEntity.ok(MessageSendResultDTO.builder().build()));

        vkMessageSenderService.sendMessage(LONG_MESSAGE, PEER_ID);

        verify(vkUriCreator).createUri(dtoCaptor.capture());
        MessageSendDTO capturedDto = dtoCaptor.getValue();

        assertThat(capturedDto.message())
                .isEqualTo("Сообщение не может быть длиннее 100 символов");

        verify(restTemplate, times(1))
                .postForEntity(eq(FAKE_URI), isNull(), eq(MessageSendResultDTO.class));
    }

    @Test
    void shouldThrowException_WhenVkReturnsError() {
        returnMaxLengthMessage();
        when(vkUriCreator.createUri(SHORT_MESSAGE)).thenReturn(FAKE_URI);
        when(restTemplate.postForEntity(eq(FAKE_URI), isNull(), eq(MessageSendResultDTO.class)))
                .thenReturn(ResponseEntity.ok(MESSAGE_ERROR_RESULT));

        assertThrows(MessageSenderException.class, () -> vkMessageSenderService.sendMessage(SHORT_MESSAGE, PEER_ID));
    }

    void returnMaxLengthMessage() {
        when(vkConfigurationApi.getMaxSizeMessage()).thenReturn(MAX_LENGTH_MESSAGE);
    }

    void returnFakeUri() {
        when(vkUriCreator.createUri(any())).thenReturn(FAKE_URI);
    }
}