CREATE TABLE sla (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fila_id BIGINT UNIQUE,
    tempo_resposta BIGINT,  -- Armazena a duração em segundos
    tempo_resolucao BIGINT, -- Armazena a duração em segundos
    FOREIGN KEY (fila_id) REFERENCES fila(id)
);
