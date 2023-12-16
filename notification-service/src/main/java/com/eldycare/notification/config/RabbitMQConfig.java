package com.eldycare.notification.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
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

	@Value("${notification.rabbitmq.notificationQueue}")
	private String notificationQueue;

	@Value("${notification.rabbitmq.notificationRoutingkey}")
	private String notificationRoutingkey;

	@Value("${notification.rabbitmq.relativeQueue}")
	private String relativeQueue;

	@Value("${notification.rabbitmq.relativeRoutingKey}")
	private String relativeRoutingKey;

	@Value("${notification.rabbitmq.exchange}")
	private String exchange;

	@Bean
	public Queue notificationQueue() {
		return new Queue(notificationQueue, true);
	}

	@Bean
	public Queue relativeQueue() {
		return new Queue(relativeQueue, true);
	}

	@Bean
	public TopicExchange exchange() {
		return new TopicExchange(exchange);
	}

	@Bean
	public Binding bindingNotification(Queue notificationQueue, TopicExchange exchange) {
		return BindingBuilder.bind(notificationQueue).to(exchange).with(notificationRoutingkey);
	}

	@Bean
	public Binding bindingRelative(Queue relativeQueue, TopicExchange exchange) {
		return BindingBuilder.bind(relativeQueue).to(exchange).with(relativeRoutingKey);
	}

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter jsonMessageConverter) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter);
		return rabbitTemplate;
	}

	@Bean
	public Jackson2JsonMessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
}
