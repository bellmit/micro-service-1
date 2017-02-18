package com.ms.eureka.core.config.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

/**
 * Created by lilufeng on 2016/8/29.
 */
//@Configuration 不使用rabbit
public class ProducerConfig {

    @Bean
    RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    Queue queueFoo(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue("queue.foo", true);
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    @Bean
    Queue queueBar(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue("queue.bar", true);
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    @Bean
    TopicExchange topicExchange(RabbitAdmin rabbitAdmin) {
        TopicExchange topicExchange = new TopicExchange("topicExchange");
        rabbitAdmin.declareExchange(topicExchange);
        return topicExchange;
    }

    @Bean
    DirectExchange directExchange(RabbitAdmin rabbitAdmin){
        DirectExchange directExchange = new DirectExchange("directExchange");
        rabbitAdmin.declareExchange(directExchange);
        return directExchange;
    }

    @Bean
    Binding bindingExchangeFoo(Queue queueFoo, DirectExchange directExchange, RabbitAdmin rabbitAdmin) {
        Binding binding = BindingBuilder.bind(queueFoo).to(directExchange).with("queue.foo");
        rabbitAdmin.declareBinding(binding);
        return binding;
    }

    @Bean
    Binding bindingExchangeBar(Queue queueBar, DirectExchange directExchange,RabbitAdmin rabbitAdmin) {
        Binding binding = BindingBuilder.bind(queueBar).to(directExchange).with("queue.bar");
        rabbitAdmin.declareBinding(binding);
        return binding;
    }

    /**
     * 生产者用
     * @return
     */
    @Bean
    public RabbitMessagingTemplate rabbitMessagingTemplate(RabbitTemplate rabbitTemplate) {
        RabbitMessagingTemplate rabbitMessagingTemplate = new RabbitMessagingTemplate();
        rabbitMessagingTemplate.setMessageConverter(jackson2Converter());
        rabbitMessagingTemplate.setRabbitTemplate(rabbitTemplate);
        return rabbitMessagingTemplate;
    }

    @Bean
    public MappingJackson2MessageConverter jackson2Converter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        return converter;
    }
}
