package com.ashish.Kafka.notification_srvice.consumer;


import com.ashish.Kafka.notification_srvice.user_service.event.UserCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserKafkaConsumer {

    public static final Logger log = LoggerFactory.getLogger(UserKafkaConsumer.class);

    @KafkaListener(topics = "user-created-topic")
    public void handleUserCreated(UserCreateEvent userCreateEvent){
        log.info("handleUserCreated :{}", userCreateEvent);
    }



    @KafkaListener(topics="user-random-topic")
    public void  handleUserRandomTopic1(String message){
        log.info("message recieved: {} ",message);

    }

    @KafkaListener(topics="user-random-topic")
    public void  handleUserRandomTopic2(String message){
        log.info("message recieved: {} ",message);
    }

    @KafkaListener(topics="user-random-topic")
    public void  handleUserRandomTopic3(String message){
        log.info("message recieved: {} ",message);
    }
}
