package com.eldycare.relative.config;

import com.eldycare.relative.listener.relativeAsyncListener;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Yassine Ouhadi
 *
 */
@Configuration
public class RabbitMQConfig {

    @Value("${relative.rabbitmq.relativeQueue}")
    private String relativeQueue;

    @Value("${relative.rabbitmq.relativeRoutingKey}")
    private String relativeRoutingKey;

    @Value("${relative.rabbitmq.exchange}")
    private String exchange;

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Queue relativeQueue() {
        return new Queue(relativeQueue, true);
    }

    @Bean
    public Queue relativeRoutingKey() {
        return new Queue(relativeRoutingKey, true);
    }

    @Bean
    public Binding bindingRelativeQueue(Queue relativeQueue, TopicExchange exchange) {
        return BindingBuilder.bind(relativeQueue).to(exchange).with(relativeRoutingKey);
    }

    @Bean
    public MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory, AmqpTemplate amqpTemplate) {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
        simpleMessageListenerContainer.setQueues(relativeQueue());
        simpleMessageListenerContainer.setMessageListener(new relativeAsyncListener(amqpTemplate));
        return simpleMessageListenerContainer;
    }
}
