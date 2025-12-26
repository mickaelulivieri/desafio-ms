package com.example.agendamento_service.producer;

import com.example.agendamento_service.config.RabbitConfig;
import com.example.agendamento_service.domain.dto.PayloadConsultaDTO;
import com.example.agendamento_service.domain.dto.PayloadExameDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendConsultaCreated(PayloadConsultaDTO dto) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE,
                RabbitConfig.RK_CLINICA_CONSULTA_CREATED,
                dto
        );
    }

    public void sendConsultaUpdated(PayloadConsultaDTO dto) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE,
                RabbitConfig.RK_CLINICA_CONSULTA_UPDATED,
                dto
        );
    }

    public void sendExameCreated(PayloadExameDTO dto) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE,
                RabbitConfig.RK_CENTRO_EXAME_CREATED,
                dto
        );
    }

    public void sendExameUpdated(PayloadExameDTO dto) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE,
                RabbitConfig.RK_CENTRO_EXAME_UPDATED,
                dto
        );
    }
}

