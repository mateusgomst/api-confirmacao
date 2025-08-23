# API de Confirmação de Agendamentos

<div align="center">

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.2-brightgreen?style=for-the-badge&logo=spring)
![Gradle](https://img.shields.io/badge/Gradle-8.14.3-blue?style=for-the-badge&logo=gradle)
![Docker](https://img.shields.io/badge/Docker-Ready-blue?style=for-the-badge&logo=docker)

**API REST para gerenciar confirmação de agendamentos de sessões terapêuticas**

</div>

---

## Informações do Projeto

- **Candidato:** Mateus Gomes Teixeira
- **Empresa:** ABA+ Inteligência Afetiva LTDA
- **Vaga:** Estágio em Desenvolvimento Java
- **Prazo:** 2 dias úteis
- **Data de Entrega:** 26/08/2025

---

## Índice

- [Descrição](#descrição)
- [Arquitetura e Design Patterns](#arquitetura-e-design-patterns)
- [Qualidade de Código](#qualidade-de-código)
- [Stack Tecnológica](#stack-tecnológica)
- [Como Executar](#como-executar)
- [Acesso à Aplicação](#acesso-à-aplicação)
- [Modelo de Dados](#modelo-de-dados)
- [API Endpoints](#api-endpoints)
- [Fluxo Completo de Teste](#fluxo-completo-de-teste)
- [Regras de Negócio](#regras-de-negócio)
- [Contato](#contato)

---

## Descrição

Sistema desenvolvido para gerenciar confirmação de agendamentos de sessões terapêuticas. O sistema permite que responsáveis confirmem a presença via links enviados por WhatsApp/Email, atualizando automaticamente o status do agendamento.

**Cenário:** A clínica agenda sessões com pacientes e precisa que os responsáveis confirmem presença com antecedência. Quando o responsável clica no link de confirmação, o status do agendamento é atualizado automaticamente.

---

## Arquitetura e Design Patterns

### **Clean Architecture**
```
Controllers → Services (Interfaces) → Repositories → Entities
```

### **Princípios SOLID Aplicados**

**Single Responsibility Principle (SRP)**
- Cada classe possui uma única responsabilidade bem definida
- Controllers focados apenas em receber/responder requisições
- Services concentram regras de negócio específicas

**Open/Closed Principle (OCP)**
- Uso de interfaces permite extensibilidade sem modificação
- Novos tipos de confirmação podem ser adicionados facilmente

**Dependency Inversion Principle (DIP)**
- Controllers dependem de abstrações (interfaces) não de implementações
- Facilita testes e manutenibilidade

### **Padrões Arquiteturais**
- **Repository Pattern** para acesso a dados
- **DTO Pattern** para transferência de dados
- **Service Layer** para lógicas de negócio
- **Exception Handler Global** para tratamento centralizado

---

## Qualidade de Código

### **Tratamento de Exceções Robusto**
- **GlobalExceptionHandler** centralizado com `@RestControllerAdvice`
- **BusinessException** customizada com status HTTP específicos
- **Validações automáticas** com Bean Validation
- **Respostas padronizadas** com timestamps e mensagens claras

### **Validações Implementadas**
- **Dados de entrada:** `@NotBlank`, `@Email`, `@Pattern`
- **Regras de negócio:** Horário funcionamento, datas futuras, duplicações
- **Status transitions:** Validação de estados válidos dos agendamentos

### **Boas Práticas**
- **Naming conventions** consistentes e descritivas
- **Separation of Concerns** bem definida
- **Immutable DTOs** para transferência segura
- **Enum types** para valores controlados
- **UUID generation** para tokens seguros
- **Swagger Documentation** para APIs autodocumentadas
- **Lombok** para código limpo e redução de boilerplate

---

## Stack Tecnológica

- **Java 17** com Spring Boot 3.3.2
- **Spring Data JPA** para persistência
- **Spring Validation** para validações automáticas
- **Swagger/OpenAPI** para documentação interativa da API
- **Lombok** para redução de boilerplate code
- **Banco H2** em memória para desenvolvimento
- **Gradle 8.14.3** como build tool
- **Docker** para containerização
- **Spring DotEnv** para gestão de variáveis ambiente

---

## Como Executar

### 1. Configurar Variáveis de Ambiente

```bash
git clone https://github.com/mateusgomst/api-confirmacao.git
cd api-confirmacao
cp .env.example .env
```

### 2. Opção 1: Ambiente Local

```bash
./gradlew bootRun
```

### 3. Opção 2: Docker

```bash
docker build -t api-confirmacao .
docker run -p 8080:8080 --env-file .env api-confirmacao
```

---

## Acesso à Aplicação

**Aplicação:** http://localhost:8080  
**Swagger UI:** http://localhost:8080/swagger-ui.html  
**H2 Console:** http://localhost:8080/h2-console

### Credenciais e Acessos
- **JDBC URL:** `jdbc:h2:mem:testdb`
- **Username:** `sa`
- **Password:** `password`
- **Swagger UI:** Documentação interativa disponível em `/swagger-ui.html`

---

## Modelo de Dados

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

## API Endpoints

**Base URL:** `http://localhost:8080`  
**Documentação Interativa:** `http://localhost:8080/swagger-ui.html`

> 💡 **Dica:** Use o Swagger UI para testar todos os endpoints de forma interativa com interface gráfica!

### **Pacientes**

#### Criar Paciente
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

#### Listar Pacientes
```bash
curl http://localhost:8080/api/pacientes
```

**Resposta (200 OK):**
```json
[
  {
    "id": 1,
    "nome": "João Silva",
    "emailResponsavel": "mae.joao@email.com",
    "telefoneResponsavel": "(62) 99999-1111"
  },
  {
    "id": 2,
    "nome": "Maria Santos",
    "emailResponsavel": "pai.maria@email.com",
    "telefoneResponsavel": "(62) 99999-2222"
  },
  {
    "id": 3,
    "nome": "UserTeste",
    "emailResponsavel": "userteste@gmail.com",
    "telefoneResponsavel": "(62) 99999-9999"
  }
]
```

---

### **Agendamentos**

#### Criar Agendamento
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

#### Listar Agendamentos
```bash
curl http://localhost:8080/api/agendamentos
```

**Resposta (200 OK):**
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
    "tokenConfirmacao": "43ad66c8-35b1-4ede-85f7-fcf486107dc4"
  }
]
```

#### Buscar Agendamento por ID
```bash
curl http://localhost:8080/api/agendamentos/1
```

**Resposta (200 OK):**
```json
{
  "id": 1,
  "pacienteNome": "João Silva",
  "dataHora": "2025-08-25T14:30:00",
  "status": "PENDENTE",
  "tokenConfirmacao": "a1b2c3d4-e5f6-7890-abcd-ef1234567890"
}
```

#### Agendamentos por Paciente
```bash
curl http://localhost:8080/api/agendamentos/paciente/1
```

**Resposta (200 OK):**
```json
[
  {
    "id": 1,
    "pacienteNome": "João Silva",
    "dataHora": "2025-08-25T14:30:00",
    "status": "PENDENTE",
    "tokenConfirmacao": "a1b2c3d4-e5f6-7890-abcd-ef1234567890"
  }
]
```

#### Cancelar Agendamento
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

### **Confirmação**

#### Enviar Confirmação
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

#### Confirmar Agendamento
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

## Fluxo Completo de Teste

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

## Regras de Negócio

### **Agendamentos**
- **Horário de funcionamento:** 08:00 às 18:00
- **Não permite agendamentos no passado**
- **Não permite duplicação** (mesmo paciente, mesma data/hora)
- **Token UUID gerado automaticamente**

### **Confirmações**
- **Só envia para agendamentos PENDENTES**
- **Não envia para CANCELADOS ou CONFIRMADOS**
- **Token válido indefinidamente**

### **Status dos Agendamentos**
- **`PENDENTE`** → Recém criado, aguardando confirmação
- **`CONFIRMADO`** → Confirmado via token pelo responsável
- **`CANCELADO`** → Cancelado manualmente

### **Validações e Tratamento de Erros**

#### **Criação de Agendamentos**
- **Paciente inexistente** → 404 NOT_FOUND
- **Data no passado** → 400 BAD_REQUEST
- **Horário fora do funcionamento** (antes 8h ou após 18h) → 400 BAD_REQUEST
- **Agendamento duplicado** (mesmo paciente/data/hora) → 409 CONFLICT
- **ID de paciente inválido** (negativo) → 400 BAD_REQUEST

#### **Envio de Confirmação**
- **Agendamento não encontrado** → 404 NOT_FOUND
- **Agendamento já confirmado** → 409 CONFLICT ("não é necessário reenviar")
- **Agendamento cancelado** → 409 CONFLICT ("não é possível enviar para cancelado")
- **Data já passou** → 400 BAD_REQUEST

#### **Confirmação via Token**
- **Token inválido/inexistente** → 404 NOT_FOUND
- **Agendamento já confirmado** → 409 CONFLICT ("já foi confirmado anteriormente")
- **Agendamento cancelado** → 409 CONFLICT ("não é possível confirmar cancelado")
- **Data já passou** → 400 BAD_REQUEST

#### **Cancelamento de Agendamentos**
- **Agendamento não encontrado** → 404 NOT_FOUND
- **Agendamento já cancelado** → 409 CONFLICT ("já foi cancelado")
- **Data já passou** → 400 BAD_REQUEST

#### **Gestão de Pacientes**
- **Nome duplicado** → 409 CONFLICT
- **Paciente não encontrado por nome** → 404 NOT_FOUND
- **Validações de entrada** → 400 BAD_REQUEST (email inválido, telefone formato incorreto)

#### **Validações de Entrada (Bean Validation)**
- **@NotBlank** para campos obrigatórios
- **@Email** para formato de email válido
- **@Pattern** para formato de telefone (XX) XXXXX-XXXX
- **@NotNull** para campos não nulos

---

## Estrutura do Projeto

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

## Contato

**Desenvolvido por:** Mateus Gomes Teixeira  
**GitHub:** [@mateusgomst](https://github.com/mateusgomst)  
**Data:** Agosto/2025  
**Empresa:** ABA+ Inteligência Afetiva LTDA