package org.example.dto.response;

import lombok.Data;
import org.example.enums.NotificationStatus;
import org.example.enums.NotificationType;

import java.time.LocalDateTime;

@Data
public class NotificationDto {
    private String notificationId;
    private String userId;
    private String content;
    private NotificationType type;
    private NotificationStatus status;
    private LocalDateTime createdAt;
    private String entityId;
}
