package dev.dwidi.springreactiverealtimemessaging.controller;

import dev.dwidi.springreactiverealtimemessaging.dto.PublicResponseDTO;
import dev.dwidi.springreactiverealtimemessaging.dto.message.MessageRequestDTO;
import dev.dwidi.springreactiverealtimemessaging.dto.message.MessageResponseDTO;
import dev.dwidi.springreactiverealtimemessaging.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/chat")
public class MessageController {

    private final MessageService messageService;
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/send")
    public Mono<PublicResponseDTO<MessageResponseDTO>> sendMessage(@RequestBody MessageRequestDTO messageRequestDTO) {
        logger.info("Received message: {}", messageRequestDTO);
        return messageService.sendMessage(messageRequestDTO);
    }

    @GetMapping(value = "/stream", produces = "text/event-stream")
    public Flux<MessageResponseDTO> streamMessages() {
        logger.info("Starting to stream messages from controller...");
        return messageService.streamMessages();
    }


    @GetMapping("/sender/{sender}")
    public Flux<PublicResponseDTO<MessageResponseDTO>> getMessagesBySender(@PathVariable String sender) {
        logger.info("Getting messages for sender: {}", sender);
        return messageService.getMessagesBySender(sender);
    }

    @GetMapping("/between/{sender}/{recipient}")
    public Flux<PublicResponseDTO<MessageResponseDTO>> getMessagesBetweenUsers(@PathVariable String sender, @PathVariable String recipient) {
        logger.info("Getting messages between sender: {} and recipient: {}", sender, recipient);
        return messageService.getMessagesBetweenUsers(sender, recipient);
    }
}
