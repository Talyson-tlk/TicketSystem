ALTER TABLE ticket
ADD COLUMN agente_id BIGINT,
ADD FOREIGN KEY (agente_id) REFERENCES agente(id);
