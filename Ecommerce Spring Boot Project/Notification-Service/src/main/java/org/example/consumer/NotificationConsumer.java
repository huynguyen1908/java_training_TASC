package org.example.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.client.UserClient;
import org.example.dto.response.UserDTO;
import org.example.entity.Notification;
import org.example.enums.NotificationStatus;
import org.example.enums.NotificationType;
import org.example.event.NotificationEvent;
import org.example.event.OrderCreatedEvent;
import org.example.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationConsumer {

    @Autowired
    private NotificationRepository notificationRepository;

    private final SimpMessagingTemplate messagingTemplate; // WebSocket
    @Autowired
    private UserClient userClient;
    @KafkaListener(topics = "new-product-topic", groupId = "notification-group")
    public void handleNotification(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        NotificationEvent event = mapper.readValue(message, NotificationEvent.class);

        if (event.isBroadcast()) {
            // 🔁 Lấy toàn bộ userId từ DB (UserService hoặc UserRepository)
            List<UserDTO> userList = userClient.getUserList(); // ví dụ

            for (UserDTO user : userList) {
                Notification notification = new Notification();
                notification.setUserId(user.getUserId());
                notification.setContent(event.getContent());
                notification.setType(event.getType());
                notification.setStatus(NotificationStatus.UNREAD);
                notification.setEntityId(event.getEntityId());
                notification.setCreatedAt(LocalDateTime.now());

                Notification saved = notificationRepository.save(notification);

                messagingTemplate.convertAndSendToUser(
                        user.getUserId(),
                        "/queue/notifications",
                        saved
                );
            }
        } else {
            Notification notification = new Notification();
            notification.setUserId(event.getUserId());
            notification.setContent(event.getContent());
            notification.setType(event.getType());
            notification.setStatus(NotificationStatus.UNREAD);
            notification.setEntityId(event.getEntityId());
            notification.setCreatedAt(LocalDateTime.now());

            Notification saved = notificationRepository.save(notification);

            messagingTemplate.convertAndSendToUser(
                    event.getUserId(),
                    "/queue/notifications",
                    saved
            );
        }
    }

    @KafkaListener(topics = "order-placed-topic", groupId = "notification-group")
    public void handleOrderNotification(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        OrderCreatedEvent event = mapper.readValue(message, OrderCreatedEvent.class);

        Notification notification = new Notification();
        notification.setUserId(event.getUserId());
        notification.setContent(event.getContent());
        notification.setType(NotificationType.ORDER_PLACED);
        notification.setStatus(NotificationStatus.UNREAD);
        notification.setEntityId(event.getOrderId());
        notification.setCreatedAt(LocalDateTime.now());

        Notification saved = notificationRepository.save(notification);

        messagingTemplate.convertAndSendToUser(
                event.getUserId(),
                "/queue/notifications",
                saved
        );
    }

    @KafkaListener(topics = "payment-notification-event", groupId = "notification-group")
    public void handlePaymentNotification(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        OrderCreatedEvent event = mapper.readValue(message, OrderCreatedEvent.class);

        Notification notification = new Notification();
        notification.setUserId(event.getUserId());
        notification.setContent(event.getContent());
        notification.setType(NotificationType.PAYMENT);
        notification.setStatus(NotificationStatus.UNREAD);
        notification.setEntityId(event.getOrderId());
        notification.setCreatedAt(LocalDateTime.now());

        Notification saved = notificationRepository.save(notification);

        messagingTemplate.convertAndSendToUser(
                event.getUserId(),
                "/queue/notifications",
                saved
        );
    }

}
