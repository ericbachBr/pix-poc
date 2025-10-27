-- ===========================================
-- Dados iniciais para a tabela de agendamentos
-- ===========================================

INSERT INTO agendamentos (
    id, id_correlacao, chave_pagador, nome_pagador, chave_recebedor, nome_recebedor, moeda, valor,
    periodicidade, data_inicio, data_fim, status, motivo, criado, atualizado
) VALUES
      (RANDOM_UUID(), 'rec-001', '123e4567-e89b-12d3-a456-426614174000', 'Maria Silva', 'abc123', 'Loja Mercado Central', 'BRL', 100.00,
       'MENSAL', CURRENT_TIMESTAMP(), NULL, 'APROVADO', 'APROVADO', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
      (RANDOM_UUID(), 'rec-002', '987e6543-e89b-12d3-a456-426614174999', 'João Souza', 'xyz789', 'Restaurante Bom Sabor', 'BRL', 250.00,
       'SEMANAL', CURRENT_TIMESTAMP(), NULL, 'PENDENTE', 'CPF INVALIDO', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

-- ===========================================
-- Dados iniciais para a tabela de auditorias
-- ===========================================

INSERT INTO auditorias (
    id, id_correlacao, tipo_evento, status_fraude, motivo_fraude, payload, data_ocorrencia
) VALUES
    (RANDOM_UUID(), 'init-001', 'INICIALIZACAO_SISTEMA', 'status_fraude', 'motivo_fraude', 'payload{}',  CURRENT_TIMESTAMP());
