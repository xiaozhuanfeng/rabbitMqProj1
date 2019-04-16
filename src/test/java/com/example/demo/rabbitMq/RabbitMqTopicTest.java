package com.example.demo.rabbitMq;

import com.example.demo.rabbitMq.exchange.topic.TopicSender;
import com.example.demo.utils.Base64Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMqTopicTest {
    @Autowired
    private TopicSender topicSender;

    @Test
    public void send1() throws Exception {
        //会匹配到topic.#和topic.message 两个Receiver都可以收到消息
        for (int i = 0, size = 10; i < size; i++) {
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
        topicSender.send4();
    }

    @Test
    public void test(){
        String ste = "发大水";
        byte[] b = ste.getBytes();
        System.out.println(Base64Utils.byte2Str(b));
        System.out.println(new String(b));
    }


}
