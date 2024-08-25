package dev.dwidi.springreactiverealtimemessaging.dto.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequestDTO {
    private String sender;
    private String recipient;
    private String content;
    private String chatRoomId;
}
