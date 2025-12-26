package com.clinica_service.service;

import com.clinica_service.domain.dto.AtendimentoRequestDTO;
import com.clinica_service.domain.dto.AtendimentoResponseDTO;
import com.clinica_service.domain.entity.ConsultaClinica;
import com.clinica_service.domain.entity.RequisicaoConsulta;
import com.clinica_service.domain.entity.Sintoma;
import com.clinica_service.exception.ConflictException;
import com.clinica_service.exception.ResourceNotFoundException;
import com.clinica_service.repository.ConsultaClinicaRepository;
import com.clinica_service.repository.RequisicaoConsultaRepository;
import com.clinica_service.repository.SintomaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConsultaService {

    private final RequisicaoConsultaRepository requisicaoRepository;
    private final ConsultaClinicaRepository consultaClinicaRepository;
    private final SintomaRepository sintomaRepository;
    private final EnvioExameService envioExameService;


    public void validarHorarioConsulta(String especialidade, LocalDateTime horario) {

        boolean ocupado = consultaClinicaRepository
                .existsByEspecialidadeAndHorario(especialidade, horario);

        if (ocupado) {
            throw new ConflictException(
                    "O médico da especialidade " + especialidade +
                            " já possui agendamento no horário " + horario
            );
        }
    }

    public void validarDisponibilidadeMedico(String especialidade, LocalDateTime horario) {
        boolean ocupado = consultaClinicaRepository.existsByEspecialidadeAndHorario(especialidade, horario);
        if (ocupado) {
            throw new ConflictException("O médico já possui agendamento no " + horario);
        }
    }


    @Transactional
    public AtendimentoResponseDTO atender(AtendimentoRequestDTO request) {

        LocalDateTime horarioAtendimento = LocalDateTime.parse(
                request.getHorario(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        );

        RequisicaoConsulta requisicao = requisicaoRepository
                .findByCpfPacienteAndHorario(request.getCpfPaciente(), horarioAtendimento)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Agendamento não encontrado para este CPF e Horário."
                        )
                );

        ConsultaClinica consultaAtendida = new ConsultaClinica();
        consultaAtendida.setAgendamentoId(requisicao.getConsultaId());
        consultaAtendida.setCpfPaciente(requisicao.getCpfPaciente());
        consultaAtendida.setEspecialidade(requisicao.getTipoExame());
        consultaAtendida.setHorario(horarioAtendimento);
        consultaAtendida.setStatus("ATENDIDA");

        consultaClinicaRepository.save(consultaAtendida);

        Map<String, Integer> scoreDoencas = new HashMap<>();

        for (String nomeSintoma : request.getSintomas()) {
            Sintoma sintoma = sintomaRepository.findByNomeIgnoreCase(nomeSintoma)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Sintoma não cadastrado: " + nomeSintoma
                            )
                    );

            String[] doencas = sintoma.getDoencasRelacionadas().split(",");
            for (String doenca : doencas) {
                String doencaNome = doenca.trim();
                scoreDoencas.merge(
                        doencaNome,
                        sintoma.getPrioridade().getNivel(),
                        Integer::sum
                );
            }
        }

        List<String> doencasSugeridas = scoreDoencas.entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        AtendimentoResponseDTO response = new AtendimentoResponseDTO();
        response.setPossiveisDoencas(doencasSugeridas);

        if (!doencasSugeridas.isEmpty()) {
            processarEncaminhamento(
                    doencasSugeridas.get(0),
                    consultaAtendida,
                    response
            );
        } else {
            response.setMensagem("Tratamento clínico simples sugerido.");
        }

        requisicaoRepository.delete(requisicao);

        return response;
    }

    public void verificarDisponibilidade(String especialidade, LocalDateTime horario) {

        boolean ocupado = consultaClinicaRepository
                .existsByEspecialidadeAndHorario(especialidade, horario);

        if (ocupado) {
            throw new ConflictException(
                    "O médico da especialidade " + especialidade +
                            " já possui agendamento no horário " + horario
            );
        }
    }

    private void processarEncaminhamento(
            String doenca,
            ConsultaClinica consulta,
            AtendimentoResponseDTO response
    ) {

        if (doenca.equalsIgnoreCase("hemorragia")
                || doenca.equalsIgnoreCase("fratura")) {

            String codigo = envioExameService.enviarAltaComplexidade(
                    consulta,
                    "CIRURGIA"
            );

            response.setCodigoExame(codigo);
            response.setMensagem(
                    "Exame registrado sobre o CPF: "
                            + consulta.getCpfPaciente()
                            + " por favor agendar o horario em nosso sistema"
            );

        } else if (doenca.equalsIgnoreCase("tumor")
                || doenca.equalsIgnoreCase("lesao interna")) {

            String codigo = envioExameService.enviarAltaComplexidade(
                    consulta,
                    "TOMOGRAFIA"
            );

            response.setCodigoExame(codigo);
            response.setMensagem(
                    "Exame registrado sobre o CPF: "
                            + consulta.getCpfPaciente()
                            + " por favor agendar o horario em nosso sistema"
            );

        } else {
            response.setMensagem(
                    "Tratamento clínico sugerido para a possível patologia: " + doenca
            );
        }
    }
}