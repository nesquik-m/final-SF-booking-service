package com.example.booking_service.mapper;

import com.example.booking_service.entity.BookingStatistics;
import com.example.booking_service.entity.UserStatistics;
import com.example.booking_service.model.KafkaBookingEvent;
import com.example.booking_service.model.KafkaUserEvent;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StatisticsMapper {

    UserStatistics kafkaUserEventToUserStatistics(KafkaUserEvent kafkaUserEvent);

    BookingStatistics kafkaBookingEventToBookingStatistics(KafkaBookingEvent kafkaBookingEvent);

}
