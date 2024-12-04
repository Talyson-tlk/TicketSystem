CREATE TABLE ticket_historico (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ticket_id BIGINT NOT NULL,
    acao TEXT,
    data_acao TIMESTAMP,
    FOREIGN KEY (ticket_id) REFERENCES ticket(id)
);

