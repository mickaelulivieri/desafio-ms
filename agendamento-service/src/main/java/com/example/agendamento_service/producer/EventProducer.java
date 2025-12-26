package com.example.agendamento_service.producer;

import com.example.agendamento_service.config.RabbitConfig;
import com.example.agendamento_service.domain.dto.PayloadConsultaDTO;
import com.example.agendamento_service.domain.dto.PayloadExameDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j // Adicionado para você conseguir ver o envio nos logs
public class EventProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendConsultaCreated(PayloadConsultaDTO dto) {
        log.info("Enviando criação de consulta para RabbitMQ: ID {}", dto.getId());
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.RK_CLINICA_CONSULTA_CREATED, dto);
    }

    public void sendConsultaUpdated(PayloadConsultaDTO dto) {
        log.info("Enviando atualização de consulta para RabbitMQ: ID {}", dto.getId());
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.RK_CLINICA_CONSULTA_UPDATED, dto);
    }

    public void sendExameCreated(PayloadExameDTO dto) {
        prepararPayloadExame(dto);
        log.info("Enviando criação de exame para RabbitMQ: ConsultaID {}", dto.getConsultaId());
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.RK_CENTRO_EXAME_CREATED, dto);
    }

    public void sendExameUpdated(PayloadExameDTO dto) {
        prepararPayloadExame(dto);
        log.info("Enviando atualização de exame para RabbitMQ: ConsultaID {}", dto.getConsultaId());
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.RK_CENTRO_EXAME_UPDATED, dto);
    }


    private void prepararPayloadExame(PayloadExameDTO dto) {
        if (dto.getPrioridade() == null) {
            dto.setPrioridade("PADRAO"); // Valor padrão para não quebrar a lógica do Centro Médico
        }
    }
}

