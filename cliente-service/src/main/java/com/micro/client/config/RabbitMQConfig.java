package com.micro.client.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {


    public static final String EXCHANGE = "microservices.exchange";


    public static final String CLIENTE_CREATED_QUEUE = "cliente.created.queue";
    public static final String CLIENTE_UPDATED_QUEUE = "cliente.updated.queue";
    public static final String CLIENTE_DELETED_QUEUE = "cliente.deleted.queue";


    public static final String CLIENTE_CREATED_ROUTING_KEY = "cliente.created";
    public static final String CLIENTE_UPDATED_ROUTING_KEY = "cliente.updated";
    public static final String CLIENTE_DELETED_ROUTING_KEY = "cliente.deleted";


    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }


    @Bean
    public Queue clienteCreatedQueue() {
        return new Queue(CLIENTE_CREATED_QUEUE, true);
    }


    @Bean
    public Queue clienteUpdatedQueue() {
        return new Queue(CLIENTE_UPDATED_QUEUE, true);
    }


    @Bean
    public Queue clienteDeletedQueue() {
        return new Queue(CLIENTE_DELETED_QUEUE, true);
    }

    @Bean
    public Binding clienteCreatedBinding() {
        return BindingBuilder
                .bind(clienteCreatedQueue())
                .to(exchange())
                .with(CLIENTE_CREATED_ROUTING_KEY);
    }


    @Bean
    public Binding clienteUpdatedBinding() {
        return BindingBuilder
                .bind(clienteUpdatedQueue())
                .to(exchange())
                .with(CLIENTE_UPDATED_ROUTING_KEY);
    }


    @Bean
    public Binding clienteDeletedBinding() {
        return BindingBuilder
                .bind(clienteDeletedQueue())
                .to(exchange())
                .with(CLIENTE_DELETED_ROUTING_KEY);
    }


    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}