package com.spring.command;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class RabbitMQConfig {

    private final String ITEM_QUEUE = "queue.item";
    private final String ITEM_US_QUEUE = "queue.item.us";
    private final String ITEM_DL_QUEUE = "queue.item.dl";
    private final String DIRECT_EXCHANGE = "direct-exchange";

   @Bean
   SimpleMessageListenerContainer container(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        return container;
    }

    @Bean
    Queue itemQueue() {
        HashMap<String,Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange", DIRECT_EXCHANGE);
        map.put("x-dead-letter-routing-key", ITEM_DL_QUEUE);
        map.put("x-message-ttl", 60000);
        map.put("x-expires", 300000);
        return new Queue(ITEM_QUEUE, false, false, false, map);
    }

    @Bean
    Queue itemDLQueue() {
        HashMap<String,Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange", DIRECT_EXCHANGE);
        map.put("x-dead-letter-routing-key", ITEM_US_QUEUE);
        map.put("x-message-ttl", 60000);
        map.put("x-expires", 300000);
        return new Queue(ITEM_DL_QUEUE, false, false, false);
    }

    @Bean
    Queue itemUSQueue() {
        return new Queue(ITEM_US_QUEUE);
    }

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE);
    }

    @Bean
    Binding itemBinding(Queue itemQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(itemQueue).to(directExchange).with(ITEM_QUEUE);
    }

    @Bean
    Binding itemDLBinding(Queue itemUSQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(itemUSQueue).to(directExchange).with(ITEM_US_QUEUE);
    }

    @Bean
    Binding itemUSBinding(Queue itemDLQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(itemDLQueue).to(directExchange).with(ITEM_DL_QUEUE);
    }
}
