package com.example.vkbot.controller;

import com.example.vkbot.dto.CallbackDTO;
import com.example.vkbot.service.CallbackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/callbacks")
@RequiredArgsConstructor
public class CallbackController {
    private final CallbackService callbackService;

    @PostMapping
    public ResponseEntity<String> handleCallback(@RequestBody CallbackDTO callbackDto) {
        return new ResponseEntity<>(callbackService.handleCallback(callbackDto), HttpStatus.OK);
    }
}
