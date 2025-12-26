package com.clinica_service.consumer;

import com.clinica_service.config.RabbitConfig;
import com.clinica_service.domain.dto.regranegocio.PayloadConsultaDTO;
import com.clinica_service.domain.entity.RequisicaoConsulta;
import com.clinica_service.repository.RequisicaoConsultaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ConsultaConsumer {

    private final RequisicaoConsultaRepository repository;

    @RabbitListener(queues = RabbitConfig.QUEUE_CLINICA)
    public void receive(PayloadConsultaDTO payload) {
        log.info("Recebendo requisição de consulta: {}", payload.getId());

        if (repository.existsByConsultaId(payload.getId())) {
            log.warn("Consulta ID {} já processada.", payload.getId());
            return;
        }

        try {
            RequisicaoConsulta entidade = new RequisicaoConsulta();


            entidade.setConsultaId(payload.getId());
            entidade.setCpfPaciente(payload.getCpfPaciente());
            entidade.setTipoExame(payload.getEspecialidadeMedico());
            entidade.setHorario(payload.getHorario());

            repository.save(entidade);
            log.info("Requisição {} salva com sucesso.", payload.getId());

        } catch (DataIntegrityViolationException e) {
            log.error("Duplicidade detectada para o ID: {}", payload.getId());
        } catch (Exception e) {
            log.error("Erro ao processar mensagem do RabbitMQ: {}", e.getMessage());
            throw e;
        }
    }
}