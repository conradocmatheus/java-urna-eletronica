# Urna Virtual API

## Visão Geral

A **Urna Virtual** é uma API RESTful desenvolvida para gerenciar votações online. O objetivo deste projeto é implementar e testar as regras de negócio relacionadas a eleitores, candidatos, votos e apuração dos resultados eleitorais.

A API oferece endpoints para gerenciar eleitores, candidatos e votos, além de realizar a apuração dos resultados de maneira automatizada.

## Funcionalidades

- **Eleitores**
  - Cadastro de eleitores com validação de dados obrigatórios (nome, profissão, telefone celular, etc.).
  - Alteração e inativação de eleitores sem exclusão física no banco de dados.
  - Alteração de status do eleitor baseado em condições específicas (APTO, INATIVO, BLOQUEADO, PENDENTE, VOTOU).
  
- **Candidatos**
  - Cadastro de candidatos com validação de CPF, número único e função (prefeito ou vereador).
  - Alteração e inativação de candidatos sem exclusão física no banco de dados.
  
- **Votos**
  - Registro de votos para candidatos a prefeito e vereador.
  - Geração de hash como comprovante de votação.
  - Alteração de status do eleitor após votação.
  - Verificação e validação de candidatos antes de registrar votos.
  
- **Apuração**
  - Apuração de votos para candidatos a prefeito e vereador.
  - Cálculo do total de votos e ordenação dos candidatos do mais votado para o menos votado.

## Regras de Negócio

### Eleitores

- O **status do eleitor** é processado automaticamente pelo sistema, com os seguintes estados:
  - **APTO**: Eleitor com cadastro completo e sem restrições para votar.
  - **INATIVO**: Atribuído quando o eleitor é "deletado". Não é possível inativar eleitores que já votaram.
  - **PENDENTE**: Atribuído quando faltam informações obrigatórias no cadastro (CPF ou email).
  - **BLOQUEADO**: Atribuído quando um eleitor com status PENDENTE tenta votar.
  - **VOTOU**: Atribuído após o eleitor realizar uma votação válida.
  
### Candidatos

- Os candidatos possuem dois status possíveis:
  - **ATIVO**: Atribuído ao cadastrar um novo candidato.
  - **INATIVO**: Atribuído quando o candidato é "deletado" sem ser removido fisicamente do banco de dados.

### Votação

- O eleitor só pode votar se tiver status **APTO**.
- A função de apuração calculará o total de votos para cada candidato e organizará os resultados.

## Estrutura do Projeto

- **Controller**: Define os endpoints da API e lida com as requisições.
- **Service**: Contém a lógica de negócio, incluindo o processamento de status e a apuração dos votos.
- **Repository**: Acesso ao banco de dados para as entidades de eleitor, candidato e voto.
- **Entity**: Representa as entidades da aplicação, como Eleitor, Candidato e Voto.

## Tecnologias Utilizadas

- **Java** (versão 17+)
- **Spring Boot** (versão 3+)
- **Hibernate** (JPA para persistência de dados)
- **H2 Database** (banco de dados em memória para testes)
- **JUnit 5** e **Mockito** (para testes unitários e de integração)
