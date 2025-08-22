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
A clínica agenda sessões com pacientes e precisa que os responsáveis confirmem presença com antecedência. Quando o responsável clica no link de confirmação, o status do agendamento é atualizado para CONFIRMADO.

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

⚠️ **IMPORTANTE: Configuração Obrigatória**  
Antes de executar a aplicação, você DEVE configurar as variáveis de ambiente:

### 📄 Configurar Variáveis de Ambiente

```bash
# Copiar o arquivo de exemplo para .env
cp .env.example .env

# O arquivo .env já contém todas as configurações necessárias
# Você pode editá-lo se necessário, mas os valores padrão funcionam perfeitamente
```

### 🔧 Opção 1: Ambiente Local (Java + Gradle)

**Pré-requisitos**
- Java 17 ou superior
- Git

**Passos**

```bash
# 1. Clonar o repositório
git clone https://github.com/mateusgomst/api-confirmacao.git
cd api-confirmacao

# 2. 🚨 OBRIGATÓRIO: Copiar configurações
cp .env.example .env

# 3. Executar a aplicação
./gradlew bootRun        # Linux/Mac
gradlew.bat bootRun      # Windows
```

### 🐳 Opção 2: Docker

**Pré-requisitos**
- Docker instalado
- Git

**Passos**

```bash
# 1. Clonar o repositório
git clone https://github.com/mateusgomst/api-confirmacao.git
cd api-confirmacao

# 2. 🚨 OBRIGATÓRIO: Copiar configurações
cp .env.example .env

# 3. Build da aplicação
./gradlew build

# 4. Build da imagem Docker
docker build -t api-confirmacao .

# 5. Executar o container com variáveis de ambiente
docker run -p 8080:8080 --env-file .env api-confirmacao
```

---

## 🌐 Acesso à Aplicação

A aplicação estará disponível em: **[http://localhost:8080](http://localhost:8080)**

### 🗄️ H2 Console (Banco de Dados)
Acesse o banco de dados em: **[http://localhost:8080/h2-console](http://localhost:8080/h2-console)**

- **JDBC URL:** `jdbc:h2:mem:testdb`
- **Username:** `sa`
- **Password:** *(deixar em branco)*

---

## 🛠️ Solução de Problemas

### ❌ Erro "Port 8080 already in use"
```bash
# Encontrar processo na porta 8080
lsof -i :8080

# Matar processo
kill -9 <PID>

# Ou alterar porta no arquivo .env
SERVER_PORT=8081
```

### ❌ Problemas de Build/Execução (Gradle)
Se encontrar erros ao rodar o projeto, execute os comandos **nesta ordem**:

```bash
# 1. Para todos os processos do Gradle
./gradlew --stop

# 2. Limpa cache e arquivos temporários
./gradlew clean

# 3. Executa a aplicação limpa
./gradlew bootRun
```

### ❌ Problema de permissão (Linux/Mac)
```bash
chmod +x gradlew
```

### ❌ Arquivo .env não encontrado
```bash
# Verificar se .env existe
ls -la .env

# Se não existir, copiar do exemplo
cp .env.example .env
```

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
├── .env.example            # 📋 Arquivo de exemplo
├── .env                    # 🚨 Copiar do .env.example
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
    status ENUM('PENDENTE', 'CONFIRMADO', 'CANCELADO') NOT NULL DEFAULT 'PENDENTE',
    token_confirmacao VARCHAR(255) UNIQUE NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (paciente_id) REFERENCES pacientes(id)
);
```

---

## 📚 Documentação da API

**Base URL:** `http://localhost:8080`

---

## 👥 Endpoints - Pacientes

### 📝 Criar Paciente
```http
POST /api/pacientes
Content-Type: application/json
```

**🔗 URL:** [http://localhost:8080/api/pacientes](http://localhost:8080/api/pacientes)

**Exemplo de Request:**
```json
{
  "nome": "UserTeste",
  "telefoneResponsavel": "(62) 99999-9999",
  "emailResponsavel": "userteste@gmail.com"
}
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
```http
GET /api/pacientes
```

**🔗 Testar no browser:** [http://localhost:8080/api/pacientes](http://localhost:8080/api/pacientes)

---

## 📅 Endpoints - Agendamentos

### 📝 Criar Agendamento
```http
POST /api/agendamentos
Content-Type: application/json
```

**🔗 URL:** [http://localhost:8080/api/agendamentos](http://localhost:8080/api/agendamentos)

**Exemplo de Request:**
```json
{
  "pacienteId": 3,
  "dataHora": "2026-08-23T15:27:50"
}
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
```http
GET /api/agendamentos
```

**🔗 Testar no browser:** [http://localhost:8080/api/agendamentos](http://localhost:8080/api/agendamentos)

**Exemplo de Response:**
```json
[
  {
    "id": 1,
    "pacienteNome": "João Silva",
    "dataHora": "2025-08-25T14:30:00",
    "status": "PENDENTE",
    "tokenConfirmacao": "a1b2c3d4-e5f6-7890-abcd-ef1234567890"
  },
  {
    "id": 2,
    "pacienteNome": "Maria Santos",
    "dataHora": "2022-08-26T09:00:00",
    "status": "PENDENTE",
    "tokenConfirmacao": "x9y8z7w6-v5u4-3210-zyxw-vu9876543210"
  },
  {
    "id": 3,
    "pacienteNome": "UserTeste",
    "dataHora": "2026-08-23T15:27:50",
    "status": "PENDENTE",
    "tokenConfirmacao": "0e98584d-cac4-45bc-a507-b9c6abc1684c"
  }
]
```

### 🔍 Buscar Agendamento por ID
```http
GET /api/agendamentos/{id}
```

**🔗 Exemplo:** [http://localhost:8080/api/agendamentos/1](http://localhost:8080/api/agendamentos/1)

### 👤 Agendamentos por Paciente
```http
GET /api/agendamentos/paciente/{pacienteId}
```

**🔗 Exemplo:** [http://localhost:8080/api/agendamentos/paciente/1](http://localhost:8080/api/agendamentos/paciente/1)

### ❌ Cancelar Agendamento
```http
PUT /api/agendamentos/{id}/cancelar
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

## ✉️ Endpoints - Confirmação

### 📤 Enviar Confirmação
```http
POST /api/agendamentos/{id}/enviar-confirmacao
```

**Exemplo URL:** `POST http://localhost:8080/api/agendamentos/3/enviar-confirmacao`

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
```http
GET /api/confirmacao/{token}
```

**🔗 Exemplo de confirmação:** [http://localhost:8080/api/confirmacao/0e98584d-cac4-45bc-a507-b9c6abc1684c](http://localhost:8080/api/confirmacao/0e98584d-cac4-45bc-a507-b9c6abc1684c)

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

## 🧪 Fluxo de Teste Completo

### **Teste via cURL**

```bash
# 1. Criar paciente
curl -X POST http://localhost:8080/api/pacientes \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João da Silva",
    "telefoneResponsavel": "(62) 99999-1234",
    "emailResponsavel": "joao@email.com"
  }'

# 2. Criar agendamento
curl -X POST http://localhost:8080/api/agendamentos \
  -H "Content-Type: application/json" \
  -d '{
    "pacienteId": 1,
    "dataHora": "2026-12-25T10:30:00"
  }'

# 3. Enviar confirmação
curl -X POST http://localhost:8080/api/agendamentos/1/enviar-confirmacao

# 4. Confirmar agendamento (use o token retornado)
curl http://localhost:8080/api/confirmacao/{token-retornado}

# 5. Cancelar agendamento
curl -X PUT http://localhost:8080/api/agendamentos/1/cancelar
```

### **Teste via Browser/Postman**

**1. Criar Paciente** → `POST http://localhost:8080/api/pacientes`
```json
{
  "nome": "UserTeste",
  "telefoneResponsavel": "(62) 99999-9999",
  "emailResponsavel": "userteste@gmail.com"
}
```

**2. Criar Agendamento** → `POST http://localhost:8080/api/agendamentos`
```json
{
  "pacienteId": 3,
  "dataHora": "2026-08-23T15:27:50"
}
```

**3. Listar Agendamentos** → [http://localhost:8080/api/agendamentos](http://localhost:8080/api/agendamentos)

**4. Enviar Confirmação** → `POST http://localhost:8080/api/agendamentos/3/enviar-confirmacao`

**5. Confirmar via Token** → `GET http://localhost:8080/api/confirmacao/{token}`

**6. Cancelar Agendamento** → `PUT http://localhost:8080/api/agendamentos/1/cancelar`

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
- ✅ **Agendamento cancelado** = erro 409
- ✅ **Data no passado** = erro 400

---

## 🎯 Dados de Exemplo

A aplicação carrega automaticamente dados de exemplo:

### 👥 Pacientes Pré-cadastrados
```sql
INSERT INTO pacientes (nome, email_responsavel, telefone_responsavel) VALUES 
('João Silva', 'mae.joao@email.com', '(62) 99999-1111'),
('Maria Santos', 'pai.maria@email.com', '(62) 99999-2222');
```

### 📅 Agendamentos Pré-cadastrados
```sql
INSERT INTO agendamentos (paciente_id, data_hora, status, token_confirmacao) VALUES 
(1, '2025-08-25 14:30:00', 'PENDENTE', 'a1b2c3d4-e5f6-7890-abcd-ef1234567890'),
(2, '2022-08-26 09:00:00', 'PENDENTE', 'x9y8z7w6-v5u4-3210-zyxw-vu9876543210');
```

---

## 🎯 Considerações Técnicas

### ⚙️ Implementação
- **Tokens:** Gerados com `UUID.randomUUID()`
- **Status:** Implementado com `@Enumerated`
- **Validações:** Spring Validation + controle de status HTTP
- **Persistência:** JPA com relacionamentos adequados
- **Arquitetura:** Separação clara de responsabilidades (Controller → Service → Repository)
- **Configuração:** Variáveis de ambiente via Spring DotEnv

### 🔧 Configuração
- **Banco H2:** Configurado em memória para desenvolvimento
- **Dados iniciais:** Carregados via `data.sql` após criação das tabelas
- **Console H2:** Habilitado para desenvolvimento
- **Porta:** Configurável via variável `SERVER_PORT`
- **Variáveis:** Definidas no arquivo `.env.example` (copiar para `.env`)

---

## 📞 Contato

**Desenvolvido por:** Mateus Gomes Teixeira  
**GitHub:** [@mateusgomst](https://github.com/mateusgomst)  
**Data:** Agosto/2025  
**Empresa:** ABA+ Inteligência Afetiva LTDA
