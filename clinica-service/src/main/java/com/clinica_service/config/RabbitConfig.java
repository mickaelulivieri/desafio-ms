package com.clinica_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE = "hospital.topic";
    public static final String QUEUE_CLINICA = "clinica.queue";
    public static final String RK_CENTRO_EXAME_CREATED = "hospital.centro.exame.created";

    public static final String RK_CONSULTA_ALL = "hospital.consulta.#";

    @Bean
    public TopicExchange hospitalExchange() {
        return new TopicExchange(EXCHANGE, true, false);
    }

    @Bean
    public Queue clinicaQueue() {
        return QueueBuilder.durable(QUEUE_CLINICA).build();
    }

    @Bean
    public Binding bindingClinica(Queue clinicaQueue, TopicExchange hospitalExchange) {
        return BindingBuilder
                .bind(clinicaQueue)
                .to(hospitalExchange)
                .with(RK_CONSULTA_ALL);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}