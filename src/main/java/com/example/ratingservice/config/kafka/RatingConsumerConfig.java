//package com.example.ratingservice.config.kafka;
//
//import com.example.ratingservice.dto.DelegationFromRidesRequest;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//
//import java.util.Map;
//
//public class RatingConsumerConfig {
//
//    @Value("${spring.kafka.consumer.bootstrap-servers}")
//    private String servers;
//
//    @Bean
//    public ConsumerFactory<String, DelegationFromRidesRequest> consumerFactory() {
//        Map<String, Object> props = Map.of(
//                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers,
////                ConsumerConfig.GROUP_ID_CONFIG,"driverGroup",
//                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
//                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class,
//                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest",
//                JsonDeserializer.VALUE_DEFAULT_TYPE, DelegationFromRidesRequest.class,
//                JsonDeserializer.TYPE_MAPPINGS, "DelegationFromRidesRequest:" + DelegationFromRidesRequest.class.getName()
//        );
//
//        return new DefaultKafkaConsumerFactory<>(props);
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, DelegationFromRidesRequest> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, DelegationFromRidesRequest> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        return factory;
//    }
//
//}
