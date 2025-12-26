CREATE TABLE tb_paciente (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cpf VARCHAR(14) NOT NULL,
    idade INT NOT NULL,
    sexo VARCHAR(20) NOT NULL
);

CREATE TABLE tb_consulta (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    paciente_id BIGINT NOT NULL,
    horario DATETIME NOT NULL,
    especialidade VARCHAR(255) NOT NULL,
    CONSTRAINT fk_consulta_paciente
        FOREIGN KEY (paciente_id)
        REFERENCES tb_paciente (id)
);

CREATE TABLE tb_exame (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo_exame VARCHAR(255) NOT NULL,
    horario DATETIME NOT NULL,
    paciente_id BIGINT NOT NULL,
    CONSTRAINT fk_exame_paciente
        FOREIGN KEY (paciente_id)
        REFERENCES tb_paciente (id)
);