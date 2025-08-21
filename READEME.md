# API de Confirmação de Agendamentos

## 📋 Informações do Projeto

**Teste Técnico - Estágio em Desenvolvimento Java**
- **Candidato:** Mateus Gomes Teixeira
- **Empresa:** ABA+
- **Prazo:** 2 dias úteis
- **Tempo Estimado:** 3-4 horas
- **Data de Entrega:** 26/08/2025

---

## 🎯 Descrição do Projeto

API REST para gerenciar confirmação de agendamentos de sessões terapêuticas. O sistema permite que responsáveis confirmem a presença via links enviados por WhatsApp/Email, atualizando automaticamente o status do agendamento.

### Cenário
A clínica agenda sessões com pacientes e precisa que os responsáveis confirmem presença com antecedência. Quando o responsável clica no link de confirmação, o status do agendamento é atualizado para **CONFIRMADO**.

---

## 🛠️ Stack Tecnológica

- **Java 11+** com Spring Boot
- **Spring Data JPA** para persistência
- **Banco H2** (em memória)
- **Gradle** como build tool

---

## 🏗️ Estrutura do Projeto

```
api-confirmacao/
├── src/main/java/com/clinic/
│   ├── controller/
│   │   ├── PacienteController.java
│   │   ├── AgendamentoController.java
│   │   └── ConfirmacaoController.java
│   ├── service/
│   │   ├── AgendamentoService.java
│   │   └── ConfirmacaoService.java
│   ├── repository/
│   ├── entity/
│   │   ├── Paciente.java
│   │   └── Agendamento.java
│   ├── dto/ (opcional)
│   └── ClinicApplication.java
├── src/main/resources/
│   ├── application.yml
│   └── data.sql
├── README.md
├── build.gradle
├── settings.gradle
└── gradlew / gradlew.bat
```

---

## 🔧 Configuração

### build.gradle
```gradle
plugins {
    id 'org.springframework.boot' version '2.7.0'
    id 'io.spring.dependency-management' version '1.0.11.RELEASE'
    id 'java'
}

group = 'com.clinic'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = '11'

repositories {
    mavenCentral()
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    runtimeOnly 'com.h2database:h2'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
}

tasks.named('test') {
    useJUnitPlatform()
}
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

## 🚀 Como Executar

### Pré-requisitos
- Java 11 ou superior
- Git

### Passos
```bash
# Clonar o repositório
git clone <url-do-repositorio>
cd api-confirmacao

# Executar a aplicação
./gradlew bootRun        # Linux/Mac
gradlew.bat bootRun      # Windows
```

A aplicação estará disponível em: `http://localhost:8080`

### H2 Console
Acesse o banco de dados em: `http://localhost:8080/h2-console`
- **JDBC URL:** `jdbc:h2:mem:testdb`
- **Username:** `sa`
- **Password:** `password`

---

## 📚 Endpoints da API

### Pacientes
```http
POST /api/pacientes      # Criar paciente
GET /api/pacientes       # Listar pacientes
```

### Agendamentos
```http
POST /api/agendamentos                     # Criar agendamento
GET /api/agendamentos                      # Listar todos agendamentos
GET /api/agendamentos/{id}                 # Buscar por ID
GET /api/agendamentos/paciente/{id}        # Agendamentos de um paciente
```

### Confirmação
```http
POST /api/agendamentos/{id}/enviar-confirmacao  # Simular envio
GET /api/confirmacao/{token}                     # Confirmar agendamento
```

---

## 🔄 Fluxo de Funcionamento

### 1. Criar Agendamento
```bash
curl -X POST http://localhost:8080/api/agendamentos \
  -H "Content-Type: application/json" \
  -d '{
    "pacienteId": 1,
    "dataHora": "2025-08-25T14:30:00"
  }'
```

**Resposta:**
```json
{
  "id": 1,
  "pacienteId": 1,
  "dataHora": "2025-08-25T14:30:00",
  "status": "PENDENTE",
  "tokenConfirmacao": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "dataCriacao": "2025-08-20T10:30:00"
}
```

### 2. Simular Envio de Confirmação
```bash
curl -X POST http://localhost:8080/api/agendamentos/1/enviar-confirmacao
```

**Resposta:**
```json
{
  "message": "Mensagem de confirmação enviada",
  "canal": "EMAIL", 
  "destinatario": "mae.joao@email.com",
  "linkConfirmacao": "http://localhost:8080/api/confirmacao/a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "conteudoMensagem": "Olá! Confirme o agendamento da sessão do João para 25/08 às 14:30. Clique: [LINK]"
}
```

### 3. Confirmar Agendamento
```bash
curl http://localhost:8080/api/confirmacao/a1b2c3d4-e5f6-7890-abcd-ef1234567890
```

**Resposta:**
```json
{
  "message": "Agendamento confirmado com sucesso!",
  "agendamento": {
    "id": 1,
    "pacienteNome": "João Silva",
    "dataHora": "2025-08-25T14:30:00",
    "status": "CONFIRMADO"
  }
}
```

---

## ⚙️ Regras de Negócio

### Status do Agendamento
- **PENDENTE:** Recém criado, aguardando confirmação
- **CONFIRMADO:** Responsável confirmou via link
- **CANCELADO:** Cancelado manualmente

### Validações
- ✅ Token único gerado automaticamente (UUID)
- ✅ Só confirma agendamentos com status PENDENTE
- ✅ Token inválido retorna erro 404
- ✅ Agendamento já confirmado retorna erro 400

### Simulação
- 📧 Não envia email/WhatsApp real
- 📱 Retorna dados simulados da mensagem

---

## 🧪 Dados de Exemplo

O sistema inclui dados pré-carregados para teste:

### Pacientes
```sql
INSERT INTO pacientes (nome, email_responsavel, telefone_responsavel) VALUES 
('João Silva', 'mae.joao@email.com', '(62) 99999-1111'),
('Maria Santos', 'pai.maria@email.com', '(62) 99999-2222');
```

### Agendamentos
```sql
INSERT INTO agendamentos (paciente_id, data_hora, status, token_confirmacao) VALUES 
(1, '2025-08-25 14:30:00', 'PENDENTE', 'token-exemplo-1'),
(2, '2025-08-26 09:00:00', 'CONFIRMADO', 'token-exemplo-2');
```

---

## 🧪 Teste Manual Completo

```bash
# 1. Criar paciente
curl -X POST http://localhost:8080/api/pacientes \
  -H "Content-Type: application/json" \
  -d '{"nome": "João Silva", "emailResponsavel": "mae.joao@email.com", "telefoneResponsavel": "(62) 99999-1111"}'

# 2. Criar agendamento
curl -X POST http://localhost:8080/api/agendamentos \
  -H "Content-Type: application/json" \
  -d '{"pacienteId": 1, "dataHora": "2025-08-25T14:30:00"}'

# 3. Simular envio de confirmação
curl -X POST http://localhost:8080/api/agendamentos/1/enviar-confirmacao

# 4. Confirmar agendamento (use o token retornado)
curl http://localhost:8080/api/confirmacao/{TOKEN_AQUI}

# 5. Tentar confirmar novamente (deve dar erro)
curl http://localhost:8080/api/confirmacao/{TOKEN_AQUI}
```

---

## 🎯 Considerações Técnicas

- **Tokens:** Gerados com `UUID.randomUUID()`
- **Status:** Implementado com `@Enumerated`
- **Validações:** Controle de status HTTP com `ResponseEntity`
- **Persistência:** JPA com relacionamentos adequados
- **Arquitetura:** Separação clara de responsabilidades (Controller → Service → Repository)

---

## 📝 Próximos Passos (Melhorias Futuras)

- [ ] Implementar envio real de email/WhatsApp
- [ ] Adicionar testes unitários
- [ ] Implementar autenticação
- [ ] Adicionar logs estruturados
- [ ] Criar interface web para administração
- [ ] Implementar cancelamento de agendamentos

---

## 🏆 Critérios Atendidos

- ✅ Projeto executa com `./gradlew bootRun`
- ✅ CRUD de pacientes funcional
- ✅ Criação de agendamentos com token automático
- ✅ Simulação de envio de confirmação
- ✅ Confirmação via token funcional
- ✅ Atualização correta de status
- ✅ Estrutura organizada em camadas
- ✅ Tratamento de erros adequado

---

**Desenvolvido por:** Mateus Gomes Teixeira  
**Data:** Agosto/2025  
**Contato:** mateusgomst