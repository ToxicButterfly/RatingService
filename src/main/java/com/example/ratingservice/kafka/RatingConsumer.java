package com.example.ratingservice.kafka;

import com.example.ratingservice.dto.DelegationFromRidesRequest;
import com.example.ratingservice.service.RatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RatingConsumer {

    private RatingService ratingService;

    @KafkaListener(topics = "${topic.name.ride}")
    public void receiveMessage(DelegationFromRidesRequest request) {
        log.info("Received message: {}", request.toString());
        ratingService.updateRating(request);
    }
}
