package com.ashish.Kafka.user_service.controller;

import com.ashish.Kafka.user_service.dto.createUserRequestDto;
import com.ashish.Kafka.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {


    @Value("${kafka.topic.user-random-topic}")
    private String KAFKA_USER_RANDOM_TOPIC;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final UserService userService;

    public UserController(KafkaTemplate<String, String> kafkaTemplate, UserService userService) {
        this.kafkaTemplate = kafkaTemplate;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody createUserRequestDto createUserRequestDto){
        userService.createUser(createUserRequestDto);
        return ResponseEntity.ok("User created successfully");
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
