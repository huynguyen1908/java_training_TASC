package org.example.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent {
    private String orderId;
    private String content;
    private String userId;
}
