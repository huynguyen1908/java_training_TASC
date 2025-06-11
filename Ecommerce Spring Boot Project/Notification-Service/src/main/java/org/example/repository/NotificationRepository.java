package org.example.repository;

import org.example.dto.response.NotificationDto;
import org.example.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {
    List<NotificationDto> findByUserIdOrderByCreatedAtDesc(String userId);
}
