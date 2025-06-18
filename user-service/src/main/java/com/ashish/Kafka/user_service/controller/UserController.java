package com.ashish.Kafka.user_service.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {


    @Value("${kafka.topic.user-random-topic}")
    private String KAFKA_USER_RANDOM_TOPIC;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public UserController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/{message}")
    public ResponseEntity<String> sendMessage(@PathVariable String message) {
        // thousand messages can be send
        for(int i =0; i < 1000 ; i++){
            kafkaTemplate.send(KAFKA_USER_RANDOM_TOPIC, message+i);
//            kafkaTemplate.send(KAFKA_USER_RANDOM_TOPIC,""+i%2, message+i);  // pass key 500 message i and 500 0 to partions
//            kafkaTemplate.send(KAFKA_USER_RANDOM_TOPIC, "" +i%3 ,message+i); // message devide 3 parts  0 333 and 1 0 and 667 mess 3


        }
        return ResponseEntity.ok("Message queued");
    }
}
