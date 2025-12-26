package com.centro_laboratorio.consumer;

import com.centro_laboratorio.config.RabbitConfig;
import com.centro_laboratorio.domain.dto.regranegocio.PayloadExameDTO;
import com.centro_laboratorio.domain.entity.Exame;
import com.centro_laboratorio.domain.entity.Procedimento;
import com.centro_laboratorio.domain.entity.RequisicaoCentroMedico;
import com.centro_laboratorio.exception.ConflictException;
import com.centro_laboratorio.repository.ExameRepository;
import com.centro_laboratorio.repository.ProcedimentoRepository;
import com.centro_laboratorio.repository.RequisicaoCentroMedicoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class CentroMedicoConsumer {

    private final RequisicaoCentroMedicoRepository recepcaoRepository;
    private final ExameRepository exameRepository;
    private final ProcedimentoRepository procedimentoRepository;

    @RabbitListener(queues = RabbitConfig.QUEUE_CENTRO)
    @Transactional
    public void receive(PayloadExameDTO payload) {
        if (recepcaoRepository.existsByConsultaId(payload.getConsultaId())) {
            log.warn("Mensagem da consulta {} já processada.", payload.getConsultaId());
            return;
        }

        RequisicaoCentroMedico recepcao = new RequisicaoCentroMedico();
        recepcao.setConsultaId(payload.getConsultaId());
        recepcao.setCpfPaciente(payload.getCpfPaciente());
        recepcao.setTipoExame(payload.getTipoExame());
        recepcao.setDataSolicitacao(payload.getHorario());
        recepcao.setStatus("RECEBIDO");
        recepcaoRepository.save(recepcao);

        try {
            String prioridade = (payload.getPrioridade() != null) ? payload.getPrioridade() : "PADRAO";

            validarDisponibilidade(payload.getHorario(), prioridade);

            if (isAltaComplexidade(payload.getTipoExame())) {
                salvarProcedimento(payload, prioridade);
                log.info("Procedimento de alta complexidade salvo: {}", payload.getTipoExame());
            } else {
                salvarExame(payload, prioridade);
                log.info("Exame simples salvo: {}", payload.getTipoExame());
            }

            recepcao.setStatus("PROCESSADO");
            recepcaoRepository.save(recepcao);

        } catch (ConflictException e) {
            recepcao.setStatus("CONFLITO");
            recepcaoRepository.save(recepcao);
            log.error("Conflito detectado: {}", e.getMessage());
        } catch (Exception e) {
            recepcao.setStatus("ERRO_INTERNO");
            recepcaoRepository.save(recepcao);
            log.error("Erro ao processar mensagem: {}", e.getMessage());
        }
    }

    private void validarDisponibilidade(LocalDateTime horario, String prioridadeNova) {
        Optional<Exame> exameExistente = exameRepository.findByHorario(horario);
        Optional<Procedimento> procExistente = procedimentoRepository.findByHorario(horario);

        boolean horarioOcupado = exameExistente.isPresent() || procExistente.isPresent();

        if (horarioOcupado) {
            if ("Emergencial".equalsIgnoreCase(prioridadeNova)) {
                boolean conflitoComOutroEmergencial =
                        (exameExistente.isPresent() && "Emergencial".equalsIgnoreCase(exameExistente.get().getPrioridade())) ||
                                (procExistente.isPresent() && "Emergencial".equalsIgnoreCase(procExistente.get().getPrioridade()));

                if (conflitoComOutroEmergencial) {
                    throw new ConflictException("O horário já possui um agendamento emergencial concomitante.");
                }
                log.info("Sobreposição permitida: Novo agendamento é Emergencial.");
            } else {
                throw new ConflictException("O horário " + horario + " já está ocupado.");
            }
        }
    }

    private boolean isAltaComplexidade(String tipo) {
        if (tipo == null) return false;
        String t = tipo.toLowerCase();
        return t.contains("ressonância") || t.contains("tomografia") || t.contains("cirurgia");
    }

    private void salvarExame(PayloadExameDTO dto, String prioridade) {
        Exame e = new Exame();
        e.setCpfPaciente(dto.getCpfPaciente());
        e.setNomeExame(dto.getTipoExame());
        e.setHorario(dto.getHorario());
        e.setPrioridade(prioridade);
        e.setAltaComplexidade(false);
        exameRepository.save(e);
    }

    private void salvarProcedimento(PayloadExameDTO dto, String prioridade) {
        Procedimento p = new Procedimento();
        p.setCpf(dto.getCpfPaciente());
        p.setNome(dto.getTipoExame());
        p.setHorario(dto.getHorario());
        p.setPrioridade(prioridade);
        procedimentoRepository.save(p);
    }
}
