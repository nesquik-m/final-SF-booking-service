package com.example.booking_service.repository;

import com.example.booking_service.entity.UserStatistics;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatisticsUserRepository extends MongoRepository<UserStatistics, Long> {

}
