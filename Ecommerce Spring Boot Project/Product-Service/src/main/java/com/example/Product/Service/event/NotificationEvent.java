package com.example.Product.Service.event;

import com.example.Product.Service.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class NotificationEvent {
    private String userId;
    private String content;
    private NotificationType type;
    private String entityId;
}

