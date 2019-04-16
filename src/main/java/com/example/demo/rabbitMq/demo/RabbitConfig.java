package com.example.demo.rabbitMq.demo;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Bean
    public Queue Queue() {
        return new Queue("mesgQue");
    }
}
