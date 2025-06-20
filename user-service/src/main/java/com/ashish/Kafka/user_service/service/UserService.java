package com.ashish.Kafka.user_service.service;

import com.ashish.Kafka.user_service.dto.createUserRequestDto;
import com.ashish.Kafka.user_service.entity.User;
import com.ashish.Kafka.user_service.event.UserCreateEvent;
import com.ashish.Kafka.user_service.repository.UserRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Value("${kafka.topic.user-created-topic}")
    private String KAFKA_USER_CREATE_TOPIC;

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final KafkaTemplate<Long, UserCreateEvent> kafkaTemplate;

    public UserService(UserRepository userRepository, ModelMapper modelMapper, KafkaTemplate<Long, UserCreateEvent> kafkaTemplate) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.kafkaTemplate = kafkaTemplate;
    }


    public void createUser(createUserRequestDto createUserRequestDto) {

        User user = modelMapper.map(createUserRequestDto,User.class);
       User savedUser= userRepository.save(user);
        UserCreateEvent userCreateEvent = modelMapper.map(savedUser, UserCreateEvent.class);
        kafkaTemplate.send(KAFKA_USER_CREATE_TOPIC,userCreateEvent.getId(), userCreateEvent);

    }
}
