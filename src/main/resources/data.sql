INSERT INTO pacientes (nome, email_responsavel, telefone_responsavel) VALUES 
('João Silva', 'mae.joao@email.com', '(62) 99999-1111'),
('Maria Santos', 'pai.maria@email.com', '(62) 99999-2222');

INSERT INTO agendamentos (paciente_id, data_hora, status, token_confirmacao) VALUES 
(1, '2025-08-25 14:30:00', 'PENDENTE', 'token-exemplo-1'),
(2, '2025-08-26 09:00:00', 'CONFIRMADO', 'token-exemplo-2');