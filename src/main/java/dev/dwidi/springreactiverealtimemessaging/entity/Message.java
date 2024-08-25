package dev.dwidi.springreactiverealtimemessaging.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "messages")
public class Message {
    @Id
    private String id;
    private String sender;
    private String recipient;
    private String content;
    private Boolean isRead;
    private String chatRoomId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
