package com.example.InventoryManagement.Configuration;

import com.example.InventoryManagement.DTO.Kafka.ProductActionDto;
import com.example.InventoryManagement.DTO.Kafka.ProductsActionDto;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Map;

@Configuration
public class KafkaConfiguration {

    @Bean
    DefaultKafkaProducerFactory<String, ProductActionDto> idProducerFactory(KafkaProperties properties) {
        Map<String, Object> producerProperties = properties.buildProducerProperties(null);
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(producerProperties);
    }

    @Bean
    KafkaTemplate<String, ProductActionDto> idKafkaTemplate(DefaultKafkaProducerFactory<String, ProductActionDto> stringProducerFactory) {
        return new KafkaTemplate<>(stringProducerFactory);
    }


    @Bean
    DefaultKafkaProducerFactory<String, ProductsActionDto> idListProducerFactory(KafkaProperties properties) {
        Map<String, Object> producerProperties = properties.buildProducerProperties(null);
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(producerProperties);
    }

    @Bean
    KafkaTemplate<String, ProductsActionDto> idListKafkaTemplate(DefaultKafkaProducerFactory<String, ProductsActionDto> idListProducerFactory) {
        return new KafkaTemplate<>(idListProducerFactory);
    }


}