CREATE TABLE horario_atendimento (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    dia_semana VARCHAR(20) NOT NULL,   
    hora_inicio VARCHAR(5) NOT NULL,   
    hora_fim VARCHAR(5) NOT NULL       
);