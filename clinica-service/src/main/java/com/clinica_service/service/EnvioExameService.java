package com.clinica_service.service;

import com.clinica_service.config.RabbitConfig;
import com.clinica_service.domain.dto.regranegocio.PayloadExameDTO;
import com.clinica_service.domain.entity.ConsultaClinica;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class EnvioExameService {

    private final RabbitTemplate rabbitTemplate;

    public String enviarAltaComplexidade(ConsultaClinica consulta, String tipo) {

        PayloadExameDTO payload = new PayloadExameDTO();
        payload.setConsultaId(consulta.getAgendamentoId());
        payload.setCpfPaciente(consulta.getCpfPaciente());
        payload.setTipoExame(tipo);
        payload.setHorario(consulta.getHorario());
        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE,
                RabbitConfig.RK_CENTRO_EXAME_CREATED
                , payload);

        log.info(
                "Exame de alta complexidade enviado. ConsultaId={}, Tipo={}, Horario={}",
                consulta.getAgendamentoId(),
                tipo,
                consulta.getHorario()
        );

        return "EXAME-" + consulta.getAgendamentoId() + "-" + LocalDateTime.now().getNano();
    }
}
