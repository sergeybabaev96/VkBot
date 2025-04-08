package com.example.vkbot.entity;

import com.example.vkbot.enums.CallbackType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MessageNewCallback {

    @Id
    private Long eventId;

    private CallbackType callbackType;

    private Long groupId;

    private Long userId;

    private String text;
}
