package dev.dwidi.springreactiverealtimemessaging.service;

import dev.dwidi.springreactiverealtimemessaging.dto.PublicResponseDTO;
import dev.dwidi.springreactiverealtimemessaging.dto.message.MessageRequestDTO;
import dev.dwidi.springreactiverealtimemessaging.dto.message.MessageResponseDTO;
import dev.dwidi.springreactiverealtimemessaging.entity.Message;
import dev.dwidi.springreactiverealtimemessaging.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.LocalDateTime;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final Sinks.Many<MessageResponseDTO> messageSink;
    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
        this.messageSink = Sinks.many().multicast().onBackpressureBuffer();
    }

    public Mono<PublicResponseDTO<MessageResponseDTO>> sendMessage(MessageRequestDTO messageRequestDTO) {
        Message newMessage = new Message();
        newMessage.setSender(messageRequestDTO.getSender());
        newMessage.setRecipient(messageRequestDTO.getRecipient());
        newMessage.setContent(messageRequestDTO.getContent());
        newMessage.setChatRoomId(messageRequestDTO.getChatRoomId());
        newMessage.setIsRead(false);
        newMessage.setCreatedAt(LocalDateTime.now());
        newMessage.setUpdatedAt(LocalDateTime.now());

        return messageRepository.save(newMessage)
                .map(savedMessage -> {
                    MessageResponseDTO messageResponseDTO = new MessageResponseDTO(savedMessage);
                    messageSink.tryEmitNext(messageResponseDTO);

                    PublicResponseDTO<MessageResponseDTO> publicResponseDTO = new PublicResponseDTO<>();
                    publicResponseDTO.setCode(200);
                    publicResponseDTO.setMessage("Message created successfully");
                    publicResponseDTO.setData(messageResponseDTO);
                    return publicResponseDTO;
                });
    }

    public Flux<MessageResponseDTO> streamMessages() {
        logger.info("Streaming messages from service...");
        return messageSink.asFlux()
                .doOnError(error -> logger.error("Error occurred in stream: {}", error.getMessage()));
    }

    public Flux<PublicResponseDTO<MessageResponseDTO>> getMessagesBySender(String sender) {
        return messageRepository.findBySender(sender)
                .flatMap(message -> {
                    message.setIsRead(true);
                    return messageRepository.save(message)
                            .map(savedMessage -> {
                                MessageResponseDTO messageResponseDTO = new MessageResponseDTO(savedMessage);
                                PublicResponseDTO<MessageResponseDTO> publicResponseDTO = new PublicResponseDTO<>();
                                publicResponseDTO.setCode(200);
                                publicResponseDTO.setMessage("Message marked as read successfully");
                                publicResponseDTO.setData(messageResponseDTO);
                                return publicResponseDTO;
                            });
                });
    }

    public Flux<PublicResponseDTO<MessageResponseDTO>> getMessagesBetweenUsers(String sender, String recipient) {
        return messageRepository.findBySenderAndRecipient(sender, recipient)
                .concatWith(messageRepository.findBySenderAndRecipient(recipient, sender))
                .cast(Message.class)
                .flatMap(message -> {
                    message.setIsRead(true);
                    return messageRepository.save(message)
                            .map(savedMessage -> {
                                MessageResponseDTO messageResponseDTO = new MessageResponseDTO(savedMessage);
                                PublicResponseDTO<MessageResponseDTO> publicResponseDTO = new PublicResponseDTO<>();
                                publicResponseDTO.setCode(200);
                                publicResponseDTO.setMessage("Message marked as read successfully");
                                publicResponseDTO.setData(messageResponseDTO);
                                return publicResponseDTO;
                            });
                });
    }
}
