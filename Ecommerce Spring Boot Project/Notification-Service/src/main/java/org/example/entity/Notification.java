package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.enums.NotificationStatus;
import org.example.enums.NotificationType;

import java.time.LocalDateTime;

@Entity
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String notificationId;

    private String userId;
    private String content;
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private NotificationStatus status;

    private LocalDateTime createdAt;
    private String entityId;
}
