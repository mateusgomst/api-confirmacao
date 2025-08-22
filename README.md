
# API de Confirmação de Agendamentos

📋 **Informações do Projeto**  
Teste Técnico - Estágio em Desenvolvimento Java

- **Candidato:** Mateus Gomes Teixeira
- **Empresa:** ABA+
- **Prazo:** 2 dias úteis
- **Tempo Estimado:** 3-4 horas
- **Data de Entrega:** 26/08/2025

🎯 **Descrição do Projeto**  
API REST para gerenciar confirmação de agendamentos de sessões terapêuticas. O sistema permite que responsáveis confirmem a presença via links enviados por WhatsApp/Email, atualizando automaticamente o status do agendamento.

**Cenário**  
A clínica agenda sessões com pacientes e precisa que os responsáveis confirmem presença com antecedência. Quando o responsável clica no link de confirmação, o status do agendamento é atualizado automaticamente.

## 🛠️ Stack Tecnológica

- Java 17 com Spring Boot 3.3.2
- Spring Data JPA para persistência
- Spring Validation para validações
- Banco H2 (em memória)
- Gradle como build tool
- Docker para containerização
- Spring DotEnv para variáveis de ambiente

---

## 🚀 Como Executar

### 📄 Configurar Variáveis de Ambiente

```bash
git clone https://github.com/mateusgomst/api-confirmacao.git
cd api-confirmacao
cp .env.example .env
```

### 🔧 Opção 1: Ambiente Local

```bash
./gradlew bootRun
```

### 🐳 Opção 2: Docker

```bash
docker build -t api-confirmacao .
docker run -p 8080:8080 --env-file .env api-confirmacao
```

---

## 🌐 Acesso à Aplicação

A aplicação estará disponível em: **http://localhost:8080**

### 🗄️ H2 Console (Banco de Dados)

Acesse o banco de dados em: **http://localhost:8080/h2-console**

- **JDBC URL:** `jdbc:h2:mem:testdb`
- **Username:** `sa`
- **Password:** `password` (conforme .env)

---

## 🏗️ Estrutura do Projeto

```
api-confirmacao/
├── src/main/java/com/aba_mais/api_confirmacao/
│   ├── controllers/
│   │   ├── PacienteController.java
│   │   ├── AgendamentoController.java
│   │   └── ConfirmacaoController.java
│   ├── services/
│   │   ├── PacienteService.java
│   │   ├── AgendamentoService.java
│   │   └── ConfirmacaoService.java
│   ├── repositories/
│   │   ├── PacienteRepository.java
│   │   └── AgendamentoRepository.java
│   ├── entities/
│   │   ├── Paciente.java
│   │   └── Agendamento.java
│   ├── dtos/
│   ├── interfaces/
│   ├── exceptions/
│   └── ApiConfirmacaoApplication.java
├── src/main/resources/
│   ├── application.properties
│   └── data.sql
├── .env.example
├── .env
├── Dockerfile
├── README.md
├── build.gradle
├── settings.gradle
└── gradlew / gradlew.bat
```

---

## 🗄️ Modelo de Dados

### Pacientes
```sql
CREATE TABLE pacientes (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    email_responsavel VARCHAR(255) NOT NULL,
    telefone_responsavel VARCHAR(20) NOT NULL
);
```

### Agendamentos
```sql
CREATE TABLE agendamentos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    paciente_id BIGINT NOT NULL,
    data_hora TIMESTAMP NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDENTE',
    token_confirmacao VARCHAR(255) UNIQUE NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (paciente_id) REFERENCES pacientes(id)
);
```

---

## 📚 API Endpoints

**Base URL:** `http://localhost:8080`

---

## 👥 Pacientes

### 📝 Criar Paciente
```bash
curl -X POST http://localhost:8080/api/pacientes \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "UserTeste",
    "telefoneResponsavel": "(62) 99999-9999",
    "emailResponsavel": "userteste@gmail.com"
  }'
```

**Resposta (201 Created):**
```json
{
  "id": 3,
  "nome": "UserTeste",
  "emailResponsavel": "userteste@gmail.com",
  "telefoneResponsavel": "(62) 99999-9999"
}
```

### 📋 Listar Pacientes
```bash
curl http://localhost:8080/api/pacientes
```

---

## 📅 Agendamentos

### 📝 Criar Agendamento
```bash
curl -X POST http://localhost:8080/api/agendamentos \
  -H "Content-Type: application/json" \
  -d '{
    "pacienteId": 3,
    "dataHora": "2026-08-23T15:27:50"
  }'
```

**Resposta (201 Created):**
```json
{
  "id": 3,
  "paciente": {
    "id": 3,
    "nome": "UserTeste",
    "emailResponsavel": "userteste@gmail.com",
    "telefoneResponsavel": "(62) 99999-9999"
  },
  "dataHora": "2026-08-23T15:27:50",
  "status": "PENDENTE",
  "tokenConfirmacao": "0e98584d-cac4-45bc-a507-b9c6abc1684c",
  "dataCriacao": "2025-08-22T18:20:23.639950116"
}
```

### 📋 Listar Agendamentos
```bash
curl http://localhost:8080/api/agendamentos
```

### 🔍 Buscar Agendamento por ID
```bash
curl http://localhost:8080/api/agendamentos/1
```

### 👤 Agendamentos por Paciente
```bash
curl http://localhost:8080/api/agendamentos/paciente/1
```

### ❌ Cancelar Agendamento
```bash
curl -X PUT http://localhost:8080/api/agendamentos/1/cancelar
```

**Resposta (200 OK):**
```json
{
  "id": 1,
  "pacienteNome": "João Silva",
  "dataHora": "2025-08-25T14:30:00",
  "status": "CANCELADO",
  "tokenConfirmacao": "a1b2c3d4-e5f6-7890-abcd-ef1234567890"
}
```

---

## ✉️ Confirmação

### 📤 Enviar Confirmação
```bash
curl -X POST http://localhost:8080/api/agendamentos/3/enviar-confirmacao
```

**Resposta (200 OK):**
```json
{
  "message": "Mensagem de confirmação enviada",
  "canal": "EMAIL",
  "destinatario": "UserTeste",
  "linkConfirmacao": "http://localhost:8080/api/confirmacao/0e98584d-cac4-45bc-a507-b9c6abc1684c",
  "conteudoMensagem": "Olá! Confirme o agendamento da sessão do UserTeste para 23/08 às 15:27. Clique: http://localhost:8080/api/confirmacao/0e98584d-cac4-45bc-a507-b9c6abc1684c"
}
```

### ✅ Confirmar Agendamento
```bash
curl http://localhost:8080/api/confirmacao/{token}
```

**Resposta (200 OK):**
```json
{
  "message": "Agendamento confirmado com sucesso!",
  "agendamento": {
    "id": 3,
    "pacienteNome": "UserTeste",
    "dataHora": "2026-08-23T15:27:50",
    "status": "CONFIRMADO",
    "tokenConfirmacao": "0e98584d-cac4-45bc-a507-b9c6abc1684c"
  }
}
```

---

## 🧪 Fluxo Completo de Teste

### 1. Criar Paciente
```bash
curl -X POST http://localhost:8080/api/pacientes \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João da Silva",
    "telefoneResponsavel": "(62) 99999-1234",
    "emailResponsavel": "joao@email.com"
  }'
```

### 2. Criar Agendamento
```bash
curl -X POST http://localhost:8080/api/agendamentos \
  -H "Content-Type: application/json" \
  -d '{
    "pacienteId": 1,
    "dataHora": "2026-12-25T10:30:00"
  }'
```

### 3. Listar Agendamentos
```bash
curl http://localhost:8080/api/agendamentos
```

### 4. Enviar Confirmação
```bash
curl -X POST http://localhost:8080/api/agendamentos/1/enviar-confirmacao
```

### 5. Confirmar via Token
```bash
curl http://localhost:8080/api/confirmacao/{token-retornado}
```

### 6. Cancelar Agendamento
```bash
curl -X PUT http://localhost:8080/api/agendamentos/1/cancelar
```

---

## ⚠️ Regras de Negócio

### 📅 Agendamentos
- ✅ **Horário de funcionamento:** 08:00 às 18:00
- ✅ **Não permite agendamentos no passado**
- ✅ **Não permite duplicação** (mesmo paciente, mesma data/hora)
- ✅ **Token UUID gerado automaticamente**

### ✉️ Confirmações
- ✅ **Só envia para agendamentos PENDENTES**
- ✅ **Não envia para CANCELADOS ou CONFIRMADOS**
- ✅ **Token válido indefinidamente**

### 🔄 Status dos Agendamentos
- **`PENDENTE`** → Recém criado, aguardando confirmação
- **`CONFIRMADO`** → Confirmado via token pelo responsável
- **`CANCELADO`** → Cancelado manualmente

### 🚫 Validações
- ✅ **Token inválido** = erro 404
- ✅ **Agendamento já confirmado** = erro 400
- ✅ **Agendamento cancelado** = retorna OK 200
- ✅ **Data no passado** = erro 400

---

## 📞 Contato

**Desenvolvido por:** Mateus Gomes Teixeira  
**GitHub:** [@mateusgomst](https://github.com/mateusgomst)  
**Data:** Agosto/2025  
**Empresa:** ABA+ Inteligência Afetiva LTDA
```