package dev.dwidi.springreactiverealtimemessaging.repository;

import dev.dwidi.springreactiverealtimemessaging.entity.Message;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface MessageRepository extends ReactiveMongoRepository<Message, String> {
    Flux<Message> findBySender(String sender);
    Flux<Object> findBySenderAndRecipient(String sender, String recipient);
}
