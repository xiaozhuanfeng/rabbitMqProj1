package com.example.demo.rabbitMq.exchange.topic;

import com.example.demo.dto.User;
import com.example.demo.utils.Base64Utils;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class TopicSender {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Resource(name = "rabbitTemplateAck")
    private AmqpTemplate rabbitTemplateAck;

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

    /**
     * 定制化amqp模板
     * @throws IOException
     */
    public void send5() throws IOException {
        User user = new User();
        user.setUserName("Sender1.....");
        user.setMobile("555555555");
        byte[] body = Base64Utils.obj2byte(user);
        Message message = new Message(body,new MessageProperties());
        rabbitTemplateAck.convertAndSend("topicExchange","byte.message",message);
    }

    public void delaySend() throws IOException {
        User user = new User();
        user.setUserName("Sender1.....");
        user.setMobile("6666666");
        byte[] body = Base64Utils.obj2byte(user);

        Message message = new Message(body,new MessageProperties());

        //延时插件https://www.rabbitmq.com/community-plugins.html
        //然后放plugin包
        //启用插件：rabbitmq-plugins enable rabbitmq_delayed_message_exchange
        //Lambda表达式
        MessagePostProcessor messagePostProcessor = message1 -> {
            //设置消息持久化
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            //message.getMessageProperties().setHeader("x-delay", 10000);
            message.getMessageProperties().setDelay(10000);
            return message;
        };

        System.out.println("发送演示消息>>>>>"+new Date());
        rabbitTemplate.convertAndSend("delayTopicExchange","delay.message",message,messagePostProcessor);
    }
}
