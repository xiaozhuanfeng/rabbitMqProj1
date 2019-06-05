package com.example.demo.rabbitMq.ack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitTemplateConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 定制化amqp模板
     * ReturnCallback接口用于实现消息发送到RabbitMQ 交换器，但无相应队列与交换器绑定时的回调  即消息发送不到任何一个队列中  ack
     * ConfirmCallback接口用于实现消息发送到RabbitMQ交换器后接收ack回调   即消息发送到exchange  ack
     *
     * @return
     */
    @Bean("rabbitTemplateAck")
    public AmqpTemplate getabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplateAck = new RabbitTemplate(connectionFactory);
        rabbitTemplateAck.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplateAck.setMandatory(true);

        //消息返回, 需要配置spring.rabbitmq.publisher-returns=true
        rabbitTemplateAck.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange,
                                        String routingKey) {
                String correlationId = message.getMessageProperties().getCorrelationId();
                logger.info("消息：{} 发送失败, 应答码：{} 原因：{} 交换机: {}  路由键: {}", correlationId, replyCode, replyText,
                        exchange, routingKey);
            }
        });


        // 消息确认, 需要配置 spring.rabbitmq.publisher-confirms=true
        rabbitTemplateAck.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                logger.info("消息发送到exchange成功");
            } else {
                logger.info("消息发送到exchange失败,原因: {}", cause);
            }
        });

        return rabbitTemplateAck;
    }

    /*@Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }*/
}
