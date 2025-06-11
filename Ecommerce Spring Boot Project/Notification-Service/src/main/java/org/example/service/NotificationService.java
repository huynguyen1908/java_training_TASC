package org.example.service;

import org.example.dto.response.NotificationDto;
import org.example.event.NotificationEvent;

import java.util.List;

public interface NotificationService {
    void sendNotification(NotificationEvent event);
    List<NotificationDto> getNotificationsByUser(String userId);
    NotificationDto getNotificationById(String id);
    void markAsRead(String id);
    void deleteNotification(String id);
}
