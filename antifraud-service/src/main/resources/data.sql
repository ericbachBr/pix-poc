-- ===========================================
-- Dados iniciais para o projeto PIX
-- ===========================================
CREATE TABLE antifraud_list (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                name VARCHAR(255) NOT NULL,
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO antifraud_list (name) VALUES
                                      ('João da Silva'),
                                      ('Maria Souza');