package com.example.demo.rabbitMq;

import com.example.demo.rabbitMq.exchange.topic.TopicSender;
import com.example.demo.utils.Base64Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMqTopicTest {
    @Autowired
    private TopicSender topicSender;

    @Test
    public void send1() throws Exception {
        //会匹配到topic.#和topic.message 两个Receiver都可以收到消息
        for (int i = 0, size = 100; i < size; i++) {
            topicSender.send1();
        }
    }

    @Test
    public void send2() throws Exception {
        //会匹配到topic.#,不会下发到Queue(topic.message) -->Receiver1收不到消息
        topicSender.send2();
    }

    @Test
    public void send3() throws Exception {
        for (int i = 0, size = 10; i < size; i++) {
            topicSender.send3();
        }
    }

    @Test
    public void send4() throws Exception {
        //topicSender.send4();
        for (int i = 0; i <10 ; i++) {
            topicSender.send4();
        }
    }

    @Test
    public void send5() throws Exception {
        for (int i = 0; i <10 ; i++) {
            topicSender.send5();
        }
    }

}
