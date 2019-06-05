package com.example.demo.rabbitMq.exchange.topic;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class TopicRabbitConfig {

    public static final String TOPIC_MESSAGE = "topic.message";
    public static final String TOPIC_MESSAGE_S = "topic.messages";
    public static final String USER_MESSAGE = "user.message";

    /**
     * 武器库
     */
    public static final String ARM_QUEUE = "arm.queue";

    public static final String BYTE_QUEUE = "byte.queue";

    /**
     * 延时Queue
     */
    public static final String DELAY_QUEUE = "delay.queue";

    @Bean
    public Queue queueTopicMessage() {
        return new Queue(TopicRabbitConfig.TOPIC_MESSAGE);
    }

    @Bean
    public Queue queueTopicMessages() {
        return new Queue(TopicRabbitConfig.TOPIC_MESSAGE_S);
    }

    @Bean
    public Queue queueUserMessage() {
        return new Queue(TopicRabbitConfig.USER_MESSAGE);
    }

    @Bean
    public Queue queueArm() {
        return new Queue(TopicRabbitConfig.ARM_QUEUE);
        //return new Queue(TopicRabbitConfig.ARM_QUEUE,false);
    }

    @Bean
    public Queue queueByte() {
        return new Queue(TopicRabbitConfig.BYTE_QUEUE);
    }

    @Bean
    public Queue delayQueue(){
        return new Queue(DELAY_QUEUE, true);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("topicExchange");
        //return new TopicExchange("topicExchange",false,false);
    }

    @Bean
    TopicExchange delayExchange() {
        Map<String, Object> pros = new HashMap<String, Object>();
        //设置交换机支持延迟消息推送
        pros.put("x-delayed-message", "topic");
        TopicExchange delayTopicExchange = new TopicExchange("delayTopicExchange",true,false,pros);

        delayTopicExchange.setDelayed(true);
        return delayTopicExchange;
    }

    @Bean
    Binding bindingExchangeMessage(Queue queueTopicMessage, TopicExchange exchange) {
        //所有匹配routingKey=topic.message的消息，将放入Queue[name="topic.message"]
        return BindingBuilder.bind(queueTopicMessage).to(exchange).with("topic.message");
    }

    @Bean
    Binding bindingExchangeMessages(Queue queueTopicMessages, TopicExchange exchange) {
        //所有匹配routingKey=topic.# 的消息，将放入Queue[name="topic.messages"]
        return BindingBuilder.bind(queueTopicMessages).to(exchange).with("topic.#");
    }

    @Bean
    Binding bindingExchangeUserMessage(Queue queueUserMessage, TopicExchange exchange) {
        ///所有匹配routingKey=user.# 的消息，将放入Queue[name="user.messages"]
        return BindingBuilder.bind(queueUserMessage).to(exchange).with("user.#");
    }

    @Bean
    Binding bindingExchangeArm(Queue queueArm, TopicExchange exchange) {
        return BindingBuilder.bind(queueArm).to(exchange).with("arm.#");
    }

    @Bean
    Binding bindingExchangeByte(Queue queueByte, TopicExchange exchange) {
        return BindingBuilder.bind(queueByte).to(exchange).with("byte.#");
    }

    @Bean
    Binding bindingDelayExchange(Queue delayQueue, TopicExchange delayExchange) {
        return BindingBuilder.bind(delayQueue).to(delayExchange).with("delay.#");
    }
}
