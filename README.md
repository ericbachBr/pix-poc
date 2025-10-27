
# PIX + AntiFraud (Gradle) - Sample Project

## 📦 Structure

- **pix-project** (port `8080`): API de agendamentos PIX, auditoria, DLQ, processamento assíncrono.
- **antifraud-service** (port `8081`): simula verificações antifraude com base em regras simples.

---


## 🚀 Como Buildar & Executar (Dev)

### Rodando localmente com Gradle:

Abra dois terminais e execute os comandos abaixo:

**Terminal 1 – AntiFraud Service (porta 8081):**
```bash
./gradlew :antifraud-service:bootRun
```
**Terminal 2 – PIX Project (porta 8080):**
```bash
./gradlew :pix-project:bootRun
```

---

## 📚 Swagger UI

- **PIX API**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **AntiFraud API**: [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)
- **H2 Console**: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

---

## 🔐 Observações

- Banco de dados **H2 em memória** para facilitar testes.
- Sem autenticação por enquanto.
- Header de correlação: `X-Correlation-Id`
- Docker Compose constrói os serviços localmente a partir dos diretórios do projeto.

---

## 📡 Endpoints

### 📌 `pix-project` (port 8080)

#### `POST /v1/agendamentos`
Cria um novo agendamento PIX.  
**Headers**: `X-Correlation-Id` obrigatório.  
**Body**:
```json
{
  "valor": 100.00,
  "moeda": "BRL",
  "chavePagador": "pagador-123",
  "nomePagador": "Maria",
  "chaveRecebedor": "recebedor-456",
  "nomeRecebedor": "Loja",
  "periodicidade": "UNICA",
  "dataInicio": "2024-11-01T10:00:00-03:00",
  "dataFim": null
}
```

#### `GET /v1/agendamentos`
Lista todos os agendamentos registrados.

#### `GET /v1/agendamentos/{id}`
Consulta um agendamento específico por ID.

---

### 📌 `antifraud-service` (port 8081)

#### `POST /api/v1/antifraud/check`
Recebe um payload com dados da transação e retorna uma análise antifraude.

**Body**:
```json
{
  "valor": 100.00,
  "chavePagador": "pagador-123",
  "chaveRecebedor": "recebedor-456",
  "moeda": "BRL"
}
```

**Response**:
```json
{
  "status": "APROVADO",
  "motivo": "Transação aprovada"
}
```

---

## 🛡️ Regras de Fraude

A lógica antifraude é aplicada com base nas seguintes regras:

1. **Valor acima de 10.000**  
   → `REVISAO MANUAL`: "Transação acima de R$10.000 requer revisão manual"

2. **Chave do pagador igual à do recebedor**  
   → `REJEITADO`: "Pagador e recebedor não podem ser a mesma chave"

3. **Moeda diferente de BRL**  
   → `REJEITADO`: "Somente transações em BRL são permitidas"

4. **Chaves PIX com menos de 5 caracteres**  
   → `REJEITADO`: "Chaves PIX inválidas (devem conter ao menos 5 caracteres)"

5. **Caso nenhuma regra seja violada**  
   → `APROVADO`: "Transação aprovada"
