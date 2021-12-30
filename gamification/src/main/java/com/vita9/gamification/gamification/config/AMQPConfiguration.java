package com.vita9.gamification.gamification.config;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;



/**
 *
 * Here we are using push model, by using @RabbitListener annotation
 * for the consumer to work, as per below doc link we need to add use @EnableRabbit
 *
 * As per https://docs.spring.io/spring-amqp/docs/current/reference/html/#async-annotation-driven-enable
 *
 */
@Configuration
@EnableRabbit
public class AMQPConfiguration implements RabbitListenerConfigurer {

    @Bean
    public TopicExchange challengesTopicExchange(
            @Value("${amqp.exchange.attempts}") final String exchangeName) {
        return ExchangeBuilder.topicExchange(exchangeName).durable(true).build();
    }

    /**
     * Creates a durable queue with name provided from the config file.
     * @param queueName
     * @return an instance of Queue
     */
    @Bean
    public Queue gamificationQueue(
            @Value("${amqp.queue.gamification}") final String queueName) {
        return QueueBuilder.durable(queueName).build();
    }

    /**
     * Binds the attemptsTopicExchange
     * to gamification queue with matching routing key
     * @param gamificationQueue
     * @param attemptsExchange
     * @return Binding
     */
    @Bean
    public Binding correctAttemptsBinding(final Queue gamificationQueue,
                                          final TopicExchange attemptsExchange) {
        return BindingBuilder.bind(gamificationQueue)
                .to(attemptsExchange)
                .with("attempt.correct");
    }


    /**
     * As per docs
     * Handling of method arguments is provided by DefaultMessageHandlerMethodFactory,
     * which you can further customize to support additional method arguments.
     * The conversion and validation support can be customized there as well
     *
     * @return MessageHandlerMethodFactory
     */
    @Bean
    public MessageHandlerMethodFactory myHandlerMethodFactory(){
        DefaultMessageHandlerMethodFactory factory =
                new DefaultMessageHandlerMethodFactory();

        // TODO explore the below code more and write unit tests
        final MappingJackson2MessageConverter jsonConverter =
                new MappingJackson2MessageConverter();
        jsonConverter.getObjectMapper().registerModule(
                new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));

        factory.setMessageConverter(jsonConverter);
        return factory;
    }



    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(myHandlerMethodFactory());
    }
}
