package com.ashish.Kafka.user_service.repository;

import com.ashish.Kafka.user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
