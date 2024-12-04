CREATE TABLE nota (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo_nota VARCHAR(50),
    conteudo TEXT NOT NULL,
    ticket_id BIGINT NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (ticket_id) REFERENCES ticket(id)
);
