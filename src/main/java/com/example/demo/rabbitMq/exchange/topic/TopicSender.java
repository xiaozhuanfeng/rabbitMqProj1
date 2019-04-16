package com.example.demo.rabbitMq.exchange.topic;

import com.example.demo.dto.User;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TopicSender {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send1() {
        User user = new User();
        user.setUserName("Sender1.....");
        user.setMobile("1111111111");
        rabbitTemplate.convertAndSend("topicExchange","topic.message",user);
    }

    public void send2() {
        User user = new User();
        user.setUserName("Sender2.....");
        user.setMobile("2222222");
        rabbitTemplate.convertAndSend("topicExchange","topic.messages",user);
    }

    public void send3() {
        User user = new User();
        user.setUserName("Sender3.....");
        user.setMobile("33333");
        rabbitTemplate.convertAndSend("topicExchange","user.message",user);
    }

    public void send4() {
        //生产一批武器
        List<String> list = new ArrayList<String>();

        list.add("手枪");
        list.add("步枪");
        list.add("机枪");

        rabbitTemplate.convertAndSend("topicExchange","arm.gun",list);
    }
}
