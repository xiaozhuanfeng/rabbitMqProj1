package com.example.demo.rabbitMq.demo;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelloSender2 {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(int i) {
        String context = "hello world....." + i;
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend("mesgQue", context);
    }
}
