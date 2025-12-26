package com.clinica_service.client;

import com.clinica_service.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AgendamentoClient {

    private final RestTemplate restTemplate;

    private static final String AGENDAMENTO_URL =
            "http://agendamento-service:8081/consultas/{id}/validar";

    public void validarConsultaNoAgendamento(Long consultaId) {
        Map<String, String> params = new HashMap<>();
        params.put("id", consultaId.toString());

        try {
            restTemplate.getForEntity(AGENDAMENTO_URL, Void.class, params);

        } catch (HttpClientErrorException.NotFound ex) {
            throw new ResourceNotFoundException(
                    "Validação falhou: Consulta ID " + consultaId + " não encontrada na 8081."
            );
        } catch (Exception ex) {
            throw new RuntimeException("Erro de conexão com Agendamento (Porta 8081): " + ex.getMessage());
        }
    }
}
