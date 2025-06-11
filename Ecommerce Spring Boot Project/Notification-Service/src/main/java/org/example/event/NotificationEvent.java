package org.example.event;

import lombok.Data;
import org.example.enums.NotificationType;

@Data
public class NotificationEvent {
    private String userId;
    private String content;
    private NotificationType type;
    private String entityId;

    public boolean isBroadcast() {
        return userId == null || userId.isBlank();
    }
}

