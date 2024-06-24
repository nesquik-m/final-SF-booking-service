package com.example.booking_service.listener;

import com.example.booking_service.mapper.StatisticsMapper;
import com.example.booking_service.model.KafkaUserEvent;
import com.example.booking_service.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaUserEventListener {

    private final StatisticsService statisticsService;

    private final StatisticsMapper statisticsMapper;

    @KafkaListener(topics = "${app.kafka.kafkaNewUserTopic}",
            groupId = "${app.kafka.kafkaMessageGroupId}",
            containerFactory = "kafkaUserEventConcurrentKafkaListenerContainerFactory")
    public void listenUserEvent(@Payload KafkaUserEvent kafkaUserEvent,
                                @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) UUID key,
                                @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                                @Header(KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp) {

        statisticsService.saveUserData(
                statisticsMapper.kafkaUserEventToUserStatistics(kafkaUserEvent));
    }

}