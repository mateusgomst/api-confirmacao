# 🎯 Passo a Passo: Sistema de Exception Handling + Validações

## ✅ Checklist Geral do Projeto

### 📋 Status Atual
- [x] Entidades criadas (Paciente, Agendamento)
- [x] DTOs implementados
- [x] POST /api/pacientes funcionando
- [x] **Exception Handler Global** ← **PRÓXIMO PASSO**
- [x] Validações na camada Service
- [x] CRUD completo de Agendamentos
- [ ] Sistema de Confirmação
- [ ] Testes manuais

---

## 🎯 **PASSO 1: Criar Exception Handler Global**

### 1.1 📁 Criar estrutura de exceções
1. Criar package `exception` dentro do projeto
2. Criar as seguintes classes:
   - `ErrorResponse.java` - classe para formato de resposta de erro
   - `ResourceNotFoundException.java` - exceção para recursos não encontrados
   - `BusinessException.java` - exceção para regras de negócio
   - `ValidationException.java` - exceção para validações
   - `GlobalExceptionHandler.java` - handler global das exceções

### 1.2 ✅ Checklist - Implementar classes de exceção

#### **ErrorResponse.java**
- [ ] Criar campos: message, status, error, path, timestamp, details
- [ ] Adicionar construtor padrão que define timestamp atual
- [ ] Adicionar construtor com parâmetros principais
- [ ] Implementar getters e setters completos

#### **ResourceNotFoundException.java**
- [ ] Estender RuntimeException
- [ ] Criar construtor que recebe mensagem simples
- [ ] Criar construtor que recebe resource, field e value para formatar mensagem automática

#### **BusinessException.java**
- [ ] Estender RuntimeException
- [ ] Criar construtor que recebe mensagem

#### **ValidationException.java**
- [ ] Estender RuntimeException
- [ ] Criar construtor que recebe mensagem

#### **GlobalExceptionHandler.java**
- [ ] Anotar com @RestControllerAdvice
- [ ] Criar método para ResourceNotFoundException (retorna 404)
- [ ] Criar método para BusinessException (retorna 400)
- [ ] Criar método para ValidationException (retorna 400)
- [ ] Criar método para MethodArgumentNotValidException (retorna 400 com detalhes)
- [ ] Criar método para Exception genérica (retorna 500)

### 1.3 ✅ Checklist - Testar Exception Handler
- [ ] Criar endpoint de teste que lança ResourceNotFoundException
- [ ] Testar se retorna JSON formatado correto
- [ ] Verificar status HTTP correto (404)
- [ ] Verificar se timestamp é preenchido automaticamente

---

## 🎯 **PASSO 2: Implementar Validações na Service**

### 2.1 ✅ Checklist - Atualizar PacienteService

#### **Adicionar validações no método criarPaciente:**
- [ ] Chamar método validarDadosPaciente antes de salvar
- [ ] Chamar método validarEmailUnico antes de salvar
- [ ] Manter o save normal após validações

#### **Criar método validarDadosPaciente:**
- [ ] Validar se nome não é null ou vazio
- [ ] Validar se nome tem pelo menos 2 caracteres
- [ ] Validar se email não é null ou vazio
- [ ] Validar formato básico do email (contém @ e .)
- [ ] Validar se telefone não é null ou vazio
- [ ] Lançar ValidationException para cada caso

#### **Criar método validarEmailUnico:**
- [ ] Verificar se já existe paciente com o email
- [ ] Lançar ValidationException se email já existe

#### **Atualizar método buscarPorId:**
- [ ] Trocar .orElse(null) por .orElseThrow(ResourceNotFoundException)

### 2.2 ✅ Checklist - Atualizar PacienteRepository
- [ ] Adicionar método `existsByEmailResponsavel(String email)`

---

## 🎯 **PASSO 3: Implementar CRUD Completo de Agendamentos**

### 3.1 ✅ Checklist - Criar AgendamentoService

#### **Método criarAgendamento:**
- [ ] Receber pacienteId e dataHora como parâmetros
- [ ] Validar se dataHora não é null
- [ ] Validar se dataHora não é no passado
- [ ] Buscar paciente pelo ID (vai lançar exceção se não existir)
- [ ] Validar se não existe agendamento duplicado para mesmo paciente/horário
- [ ] Criar objeto Agendamento com status PENDENTE
- [ ] Gerar token UUID automático
- [ ] Definir dataCriacao como now()
- [ ] Salvar e retornar agendamento

#### **Outros métodos:**
- [ ] `listarTodos()` - simples findAll()
- [ ] `buscarPorId()` - com ResourceNotFoundException
- [ ] `buscarPorPaciente()` - verificar se paciente existe primeiro

### 3.2 ✅ Checklist - Criar AgendamentoRepository
- [ ] Estender JpaRepository<Agendamento, Long>
- [ ] Adicionar método `findByPacienteId(Long pacienteId)`
- [ ] Adicionar método `existsByPacienteIdAndDataHora(Long pacienteId, LocalDateTime dataHora)`
- [ ] Adicionar método `findByTokenConfirmacao(String token)`

### 3.3 ✅ Checklist - Criar DTOs para Agendamento

#### **AgendamentoCreateDTO:**
- [ ] Campo pacienteId com @NotNull
- [ ] Campo dataHora com @NotNull e @JsonFormat
- [ ] Getters e setters

#### **AgendamentoResponseDTO:**
- [ ] Campos: id, pacienteId, pacienteNome, dataHora, status, tokenConfirmacao, dataCriacao
- [ ] @JsonFormat nas datas
- [ ] Construtores e getters/setters

### 3.4 ✅ Checklist - Criar AgendamentoController
- [ ] Anotar com @RestController e @RequestMapping("/api/agendamentos")
- [ ] POST / - recebe DTO, chama service, retorna 201 Created
- [ ] GET / - lista todos, converte para DTO
- [ ] GET /{id} - busca por ID, converte para DTO
- [ ] GET /paciente/{pacienteId} - busca por paciente, converte para DTO
- [ ] Criar método auxiliar convertToDTO

---

## 🎯 **PASSO 4: Sistema de Confirmação**

### 4.1 ✅ Checklist - Criar ConfirmacaoService

#### **Método simularEnvioConfirmacao:**
- [ ] Receber agendamentoId
- [ ] Buscar agendamento (lançar exceção se não existir)
- [ ] Verificar se status é PENDENTE (lançar BusinessException se não for)
- [ ] Gerar mensagem de confirmação com nome do paciente, data/hora formatada e link
- [ ] Retornar a mensagem

#### **Método confirmarAgendamento:**
- [ ] Receber token como parâmetro
- [ ] Buscar agendamento pelo token (lançar exceção se não existir)
- [ ] Verificar se não está já confirmado (lançar BusinessException)
- [ ] Verificar se não está cancelado (lançar BusinessException)
- [ ] Alterar status para CONFIRMADO
- [ ] Salvar e retornar agendamento

### 4.2 ✅ Checklist - Criar ConfirmacaoController
- [ ] Anotar com @RestController e @RequestMapping("/api")
- [ ] POST /agendamentos/{id}/enviar-confirmacao
  - [ ] Chamar service para simular envio
  - [ ] Retornar Map com message, canal e conteudoMensagem
- [ ] GET /confirmacao/{token}
  - [ ] Chamar service para confirmar
  - [ ] Retornar Map com message e dados do agendamento

---

## 🎯 **PASSO 5: Checklist Final de Testes**

### 5.1 ✅ Testar Exception Handler
- [ ] Buscar paciente inexistente: `GET /api/pacientes/999`
- [ ] Criar paciente com email duplicado
- [ ] Criar paciente com dados inválidos (nome vazio, email inválido)
- [ ] Verificar se JSON de erro tem todos os campos corretos

### 5.2 ✅ Testar CRUD Pacientes
- [ ] POST /api/pacientes (dados válidos - deve funcionar)
- [ ] POST /api/pacientes (email duplicado - deve dar erro 400)
- [ ] POST /api/pacientes (dados inválidos - deve dar erro 400)
- [ ] GET /api/pacientes (deve listar todos)
- [ ] GET /api/pacientes/{id} (ID válido - deve funcionar)
- [ ] GET /api/pacientes/{id} (ID inválido - deve dar erro 404)

### 5.3 ✅ Testar CRUD Agendamentos
- [ ] POST /api/agendamentos (dados válidos - deve criar com token)
- [ ] POST /api/agendamentos (paciente inexistente - deve dar erro 404)
- [ ] POST /api/agendamentos (data passada - deve dar erro 400)
- [ ] POST /api/agendamentos (mesma data/paciente - deve dar erro 400)
- [ ] GET /api/agendamentos (deve listar todos)
- [ ] GET /api/agendamentos/{id} (deve mostrar detalhes)
- [ ] GET /api/agendamentos/paciente/{id} (deve filtrar por paciente)

### 5.4 ✅ Testar Sistema de Confirmação
- [ ] POST /api/agendamentos/{id}/enviar-confirmacao (deve simular envio)
- [ ] GET /api/confirmacao/{token} (primeira vez - deve confirmar)
- [ ] GET /api/confirmacao/{token} (segunda vez - deve dar erro 400)
- [ ] GET /api/confirmacao/token-invalido (deve dar erro 404)

---

## 🚀 **Próximos Passos Após Completar**

1. **Atualizar README com novos endpoints**
2. **Criar/atualizar data.sql com dados de exemplo**
3. **Testar fluxo completo com Postman/curl**
4. **Verificar logs no console H2**
5. **Preparar documentação final para entrega**