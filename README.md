ViaCEP Fullstack - Buscador de Endereços
Aplicação fullstack desenvolvida como desafio do curso Oracle Next Education (ONE) + Alura. Backend Java puro com servidor HTTP nativo, frontend React + TypeScript.

![Demonstração da aplicação](https://raw.githubusercontent.com/paulojrtoledo/fullstack-viacep/main/frontend/gif-viacep-front-working.gif)

🎯 Objetivo
Transformar um desafio Java de console em aplicação web completa, demonstrando integração entre backend e frontend sem uso de frameworks pesados.

🏗️ Arquitetura
text
Cliente (React) → Backend (Java HTTP Server) → API ViaCEP
         ↑                    ↓
    Interface Web       Resposta JSON
📦 Tecnologias
Backend
Java 17+ - Linguagem principal

HttpServer - Servidor HTTP nativo do JDK

Gson 2.13.2 - Serialização JSON

HttpClient - Requisições HTTP para ViaCEP

Frontend
React 18 - Biblioteca UI

TypeScript - Tipagem estática

Tailwind CSS - Estilização utilitária

Vite - Build tool

🚀 Execução Local
Pré-requisitos
JDK 17 ou superior

Node.js 18+ e npm

Passo 1: Iniciar Backend
bash
cd backend/challenge-api-viacep/src
javac -cp "gson-2.13.2.jar" *.java br/com/alura/exceptions/*.java
java -cp "gson-2.13.2.jar:." SearchMain
Servidor disponível em: http://localhost:8080

Passo 2: Iniciar Frontend
bash
cd frontend
npm install
npm run dev
Acesse: http://localhost:5173

🔌 Endpoints da API
GET /api/cep/{cep}
Consulta endereço pelo CEP.

Exemplo:

bash
curl http://localhost:8080/api/cep/01310100
Resposta:

json
{
  "cep": "01310-100",
  "logradouro": "Avenida Paulista",
  "complemento": "de 612 a 1510 - lado par",
  "bairro": "Bela Vista",
  "localidade": "São Paulo",
  "uf": "SP",
  "ibge": "3550308",
  "gia": "1004",
  "ddd": "11",
  "siafi": "7107"
}
💡 Funcionalidades
Backend
Servidor HTTP multi-threaded

Tratamento de erros HTTP (400, 404, 500)

CORS configurado

Conexão com API ViaCEP

Serialização JSON automática

Frontend
Input com validação de CEP

Busca por Enter ou botão

Card responsivo com dados

Campo de complemento editável

Estados de carregamento/erro

Design com gradientes e transições

🧩 Estrutura de Código
Backend (backend/challenge-api-viacep/src/)
SearchMain.java - Inicializa servidor

CepHandler.java - Processa requisições HTTP

CepResult.java - Modelo de dados

CepNotFoundException.java - Exceção customizada

Frontend (frontend/src/)
App.tsx - Componente principal

App.css - Estilos complementares

main.tsx - Ponto de entrada React

⚙️ Configuração para Deploy
Variáveis de Ambiente
PORT - Porta do servidor Java (default: 8080)

VITE_API_URL - URL do backend (frontend)

Ajuste para Render/Heroku
Modificar SearchMain.java:

java
int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080"));
📊 Decisões Técnicas
Java sem Spring - Demonstrar funcionamento HTTP de baixo nível

HttpServer nativo - Evitar overhead de frameworks

CORS manual - Entendimento completo do protocolo

TypeScript no frontend - Segurança de tipos

Tailwind CSS - Estilização rápida e consistente

🐛 Solução de Problemas
Erro de Compilação
bash
error: package com.google.gson does not exist
Solução: Certifique-se que gson-2.13.2.jar está na mesma pasta dos arquivos .java.

CORS Blocked
Solução: O backend já inclui Access-Control-Allow-Origin: * nas respostas.

Porta em Uso
bash
java.net.BindException: Address already in use
Solução: Altere a porta no backend ou feche o processo na porta 8080.

📈 Próximos Passos
Adicionar testes unitários (JUnit)

Implementar cache de consultas

Adicionar autenticação básica

Dockerizar aplicação

CI/CD com GitHub Actions

📄 Licença
MIT License - veja o arquivo LICENSE para detalhes.

👥 Contribuição
Fork o projeto

Crie uma branch (git checkout -b feature/nova-funcionalidade)

Commit suas mudanças (git commit -m 'Add nova funcionalidade')

Push para a branch (git push origin feature/nova-funcionalidade)

Abra um Pull Request

Desenvolvido como parte do programa Oracle Next Education + Alura.

