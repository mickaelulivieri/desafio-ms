package com.centro_laboratorio.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE = "hospital.topic";
    public static final String QUEUE_CENTRO = "centro.cirurgico.queue";

    public static final String RK_EXAME_ALL = "hospital.exame.#";
    public static final String RK_PROCEDIMENTO_ALL = "hospital.procedimento.#";

    @Bean
    public TopicExchange hospitalExchange() {
        return new TopicExchange(EXCHANGE, true, false);
    }

    @Bean
    public Queue centroQueue() {
        return QueueBuilder.durable(QUEUE_CENTRO).build();
    }

    @Bean
    public Binding bindingExames(Queue centroQueue, TopicExchange hospitalExchange) {
        return BindingBuilder.bind(centroQueue).to(hospitalExchange).with(RK_EXAME_ALL);
    }

    @Bean
    public Binding bindingProcedimentos(Queue centroQueue, TopicExchange hospitalExchange) {
        return BindingBuilder.bind(centroQueue).to(hospitalExchange).with(RK_PROCEDIMENTO_ALL);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
