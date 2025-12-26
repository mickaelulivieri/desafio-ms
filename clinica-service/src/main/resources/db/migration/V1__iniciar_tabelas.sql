-- Criação da tabela de Médicos para o CRUD obrigatório [cite: 93]
CREATE TABLE IF NOT EXISTS tb_medico (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    especialidade VARCHAR(255) NOT NULL,
    crm VARCHAR(50) NOT NULL UNIQUE
) ENGINE=InnoDB;

-- Criação da tabela de Sintomas para lógica de diagnóstico e prioridade [cite: 96, 97]
CREATE TABLE IF NOT EXISTS tb_sintoma (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    doencas_relacionadas TEXT, -- Armazena as associações de doenças [cite: 97]
    prioridade VARCHAR(50) NOT NULL -- Níveis 1 a 4 (Enum)
) ENGINE=InnoDB;

-- Tabela de Staging para receber dados da API de Agendamento [cite: 94, 99]
CREATE TABLE IF NOT EXISTS db_requisicao_consulta (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    consulta_id BIGINT NOT NULL UNIQUE, -- Unique garante a idempotência
    cpf_paciente VARCHAR(14) NOT NULL,
    tipo_exame VARCHAR(255), -- Representa a especialidade solicitada
    horario DATETIME NOT NULL
) ENGINE=InnoDB;

-- Tabela para persistir o histórico de consultas atendidas [cite: 91, 103]
CREATE TABLE IF NOT EXISTS tb_consulta_atendida (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    agendamento_id BIGINT NOT NULL,
    cpf_paciente VARCHAR(14) NOT NULL,
    especialidade VARCHAR(255) NOT NULL,
    horario DATETIME NOT NULL,
    status VARCHAR(50) DEFAULT 'ATENDIDA'
) ENGINE=InnoDB;