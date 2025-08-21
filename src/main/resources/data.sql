INSERT INTO pacientes (nome, email_responsavel, telefone_responsavel) VALUES
('João Silva', 'mae.joao@email.com', '(62) 99999-1111'),
('Maria Santos', 'pai.maria@email.com', '(62) 99999-2222');

INSERT INTO agendamentos (paciente_id, data_hora, status, token_confirmacao, data_criacao) VALUES
(1, TIMESTAMP '2025-08-25 14:30:00', 'PENDENTE', 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', TIMESTAMP '2025-08-21 13:15:50'),
(2, TIMESTAMP '2025-08-26 09:00:00', 'CONFIRMADO', 'x9y8z7w6-v5u4-3210-zyxw-vu9876543210', TIMESTAMP '2025-08-21 13:15:50')