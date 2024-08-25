package dev.dwidi.springreactiverealtimemessaging.dto.message;

import dev.dwidi.springreactiverealtimemessaging.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponseDTO {
    private String id;
    private String sender;
    private String recipient;
    private String content;
    private Boolean isRead;
    private String chatRoomId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public MessageResponseDTO(Message message) {
        this.id = message.getId();
        this.sender = message.getSender();
        this.recipient = message.getRecipient();
        this.content = message.getContent();
        this.isRead = message.getIsRead();
        this.chatRoomId = message.getChatRoomId();
        this.createdAt = message.getCreatedAt();
        this.updatedAt = message.getUpdatedAt();
    }
}
