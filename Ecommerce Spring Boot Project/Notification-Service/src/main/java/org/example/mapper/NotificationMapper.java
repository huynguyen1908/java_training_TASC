package org.example.mapper;

import org.example.dto.response.NotificationDto;
import org.example.entity.Notification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    Notification toNotification(NotificationDto dto);
    NotificationDto toDto(Notification notification);

}
