package org.example.service;

import org.example.client.UserClient;
import org.example.dto.response.NotificationDto;
import org.example.dto.response.UserDTO;
import org.example.entity.Notification;
import org.example.enums.NotificationStatus;
import org.example.event.NotificationEvent;
import org.example.mapper.NotificationMapper;
import org.example.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService{
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private UserClient userClient;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Override
    public void sendNotification(NotificationEvent event){
        if (event.isBroadcast()) {
            List<UserDTO> users = userClient.getUserList();
            for (UserDTO user : users) {
                saveAndSend(user.getUserId(), event);
            }
        } else {
            saveAndSend(event.getUserId(), event);
        }
    }

    private void saveAndSend(String userId, NotificationEvent event) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setContent(event.getContent());
        notification.setType(event.getType());
        notification.setStatus(NotificationStatus.UNREAD);
        notification.setEntityId(event.getEntityId());
        notification.setCreatedAt(LocalDateTime.now());

        Notification saved = notificationRepository.save(notification);

        messagingTemplate.convertAndSendToUser(
                userId,
                "/queue/notifications",
                saved
        );
    }

    @Override
    public List<NotificationDto> getNotificationsByUser(String userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Override
    public NotificationDto getNotificationById(String id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        return notificationMapper.toDto(notification);
    }

    @Override
    public void markAsRead(String id) {
        NotificationDto notificationDto = getNotificationById(id);
        notificationDto.setStatus(NotificationStatus.READ);
        notificationRepository.save(notificationMapper.toNotification(notificationDto));
    }

    @Override
    public void deleteNotification(String id) {
        notificationRepository.deleteById(id);
    }


}
