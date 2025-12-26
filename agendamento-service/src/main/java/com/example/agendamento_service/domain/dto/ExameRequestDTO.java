package com.example.agendamento_service.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class ExameRequestDTO {

    @NotBlank(message = "O tipo do exame é obrigatório")
    private String tipoExame;

    @NotNull(message = "O horário é obrigatório")
    private LocalDateTime horario;

    @NotNull(message = "O ID do paciente é obrigatório")
    private Long pacienteId;

    public String getTipoExame() { return tipoExame; }
    public void setTipoExame(String tipoExame) { this.tipoExame = tipoExame; }

    public LocalDateTime getHorario() { return horario; }
    public void setHorario(LocalDateTime horario) { this.horario = horario; }

    public Long getPacienteId() { return pacienteId; }
    public void setPacienteId(Long pacienteId) { this.pacienteId = pacienteId; }
}
