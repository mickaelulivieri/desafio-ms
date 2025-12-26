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
        // 1. Idempotência: Verifica se a consulta já foi processada para evitar duplicados
        if (recepcaoRepository.existsByConsultaId(payload.getConsultaId())) {
            log.warn("Mensagem da consulta {} já processada ou em andamento.", payload.getConsultaId());
            return;
        }

        // 2. Registro de Staging: Salva o início do processamento
        RequisicaoCentroMedico recepcao = new RequisicaoCentroMedico();
        recepcao.setConsultaId(payload.getConsultaId());
        recepcao.setCpfPaciente(payload.getCpfPaciente());
        recepcao.setTipoExame(payload.getTipoExame());
        recepcao.setDataSolicitacao(payload.getHorario());
        recepcao.setStatus("RECEBIDO");
        recepcaoRepository.save(recepcao);

        try {
            // 3. Tratamento de Payload: Garante que a prioridade nunca seja nula
            String prioridade = (payload.getPrioridade() != null) ? payload.getPrioridade() : "PADRAO";

            // 4. Validação de Regra de Negócio: Verifica conflitos de horário [cite: 173-176, 181-182]
            validarDisponibilidade(payload.getHorario(), prioridade);

            // 5. Roteamento de Entidade: Decide se salva como Exame ou Procedimento
            if (isAltaComplexidade(payload.getTipoExame())) {
                salvarProcedimento(payload, prioridade);
                log.info("Procedimento de ALTA COMPLEXIDADE salvo: {}", payload.getTipoExame());
            } else {
                salvarExame(payload, prioridade);
                log.info("EXAME SIMPLES salvo: {}", payload.getTipoExame());
            }

            // 6. Finalização: Atualiza status para sucesso
            recepcao.setStatus("PROCESSADO");
            recepcaoRepository.save(recepcao);

        } catch (ConflictException e) {
            recepcao.setStatus("CONFLITO");
            recepcaoRepository.save(recepcao);
            log.error("Conflito de agendamento detectado: {}", e.getMessage());
        } catch (Exception e) {
            recepcao.setStatus("ERRO_INTERNO");
            recepcaoRepository.save(recepcao);
            log.error("Erro crítico ao processar mensagem da consulta {}: {}", payload.getConsultaId(), e.getMessage());
        }
    }

    private void validarDisponibilidade(LocalDateTime horario, String prioridadeNova) {
        // Consulta ambos os repositórios para garantir que o horário está livre
        Optional<Exame> exameExistente = exameRepository.findByHorario(horario);
        Optional<Procedimento> procExistente = procedimentoRepository.findByHorario(horario);

        boolean horarioOcupado = exameExistente.isPresent() || procExistente.isPresent();

        if (horarioOcupado) {
            // Regra Especial: Emergenciais podem sobrepor qualquer um, exceto outros emergenciais [cite: 175-176, 181-182]
            if ("Emergencial".equalsIgnoreCase(prioridadeNova)) {
                boolean conflitoComOutroEmergencial =
                        (exameExistente.isPresent() && "Emergencial".equalsIgnoreCase(exameExistente.get().getPrioridade())) ||
                                (procExistente.isPresent() && "Emergencial".equalsIgnoreCase(procExistente.get().getPrioridade()));

                if (conflitoComOutroEmergencial) {
                    throw new ConflictException("O horário já possui um agendamento emergencial concomitante.");
                }
                log.info("Sobreposição permitida devido à prioridade Emergencial.");
            } else {
                throw new ConflictException("O horário solicitado (" + horario + ") já está ocupado no laboratório.");
            }
        }
    }

    private boolean isAltaComplexidade(String tipo) {
        if (tipo == null) return false;
        String t = tipo.toLowerCase();
        // Critérios definidos pelo desafio
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