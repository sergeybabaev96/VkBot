package com.example.vkbot.controller;

import com.example.vkbot.dto.CallbackDTO;
import com.example.vkbot.service.CallbackService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.vkbot.service.CallbackServiceTestConstant.MESSAGE_NEW;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CallbackController.class)
@Import(CallbackControllerIntegrationTest.TestConfig.class)
class CallbackControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CallbackService callbackService;

    @Test
    void shouldReturnOkResponse() throws Exception {
        when(callbackService.handleCallback(any(CallbackDTO.class))).thenReturn("ok");

        mockMvc.perform(post("/api/v1/callbacks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(MESSAGE_NEW)))
                .andExpect(status().isOk())
                .andExpect(content().string("ok"));
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public CallbackService callbackService() {
            return mock(CallbackService.class);
        }
    }
}
