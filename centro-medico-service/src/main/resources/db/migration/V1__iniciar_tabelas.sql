-- Tabela de Recepção (Idempotência e Staging)
CREATE TABLE IF NOT EXISTS db_requisicao_centro_medico (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    consulta_id BIGINT NOT NULL UNIQUE,
    cpf_paciente VARCHAR(14) NOT NULL,
    tipo_exame VARCHAR(255) NOT NULL,
    data_solicitacao DATETIME NOT NULL,
    status_processamento VARCHAR(50)
) ENGINE=InnoDB;

-- Tabela de Exames Simples
CREATE TABLE IF NOT EXISTS tb_exames (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cpf_paciente VARCHAR(14) NOT NULL,
    nome_exame VARCHAR(255) NOT NULL,
    horario DATETIME NOT NULL,
    prioridade VARCHAR(50),
    alta_complexidade BOOLEAN DEFAULT FALSE
) ENGINE=InnoDB;

-- Tabela de Procedimentos/Cirurgias (Alta Complexidade)
CREATE TABLE IF NOT EXISTS tb_procedimentos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cpf VARCHAR(14) NOT NULL,
    nome VARCHAR(255) NOT NULL,
    prioridade VARCHAR(50),
    horario DATETIME NOT NULL
) ENGINE=InnoDB;