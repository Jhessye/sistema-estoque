# Sistema de Gerenciamento de Estoque

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)
[![NetBeans](https://img.shields.io/badge/NetBeans-1B6AC6?style=for-the-badge&logo=apache-netbeans-ide&logoColor=white)](https://netbeans.apache.org/)

Este projeto é um **sistema de gerenciamento de estoque** desenvolvido para uma loja de manutenção de automóveis.  
Ele permite o **controle de categorias, produtos e movimentações** de itens, oferecendo funcionalidades completas de **cadastro, atualização, exclusão e consulta**.  

O sistema foi desenvolvido em **Java (NetBeans)**, com integração a um **banco de dados PostgreSQL** rodando em **Docker**.

> **Pré-requisito**: é necessário ter o Docker e o git instalado e em execução na máquina para que o banco de dados funcione corretamente. JDK 21.

---
> LINK DO VÍDEO DO FUNCIONAMENTO DO SISTEMA: https://youtu.be/VKXNtzNwChs
---

## Executando o projeto no Linux

1. Inicie o terminal (ctrl + alt + t) e execute o seguinte comando para clonar o repositório do Github e entrar dentro da pasta: 
```
git clone https://github.com/Jhessye/sistema-estoque.git && cd sistema-estoque
```
2.  Inicie o docker compose do banco de dados Postgres em modo detached com o comando:
```
docker compose -f banco_de_dados/docker-compose.yaml up -d
```
3. Vá até a pasta "run":
```
cd run
```
4. Dê permissão de execução para o arquivo usando chmod:
```
chmod +x Iniciar_Linux.sh
```
5. Usando o explorador de arquivo gráfico, vá até o script shell e execute, ou execute ele diretamente do terminal com:
```
./Iniciar_Linux.sh
```
Pronto! O programa irá aparecer na tela.

---

## Executando o projeto no Windows

1. Abra o **Prompt de Comando** ou **PowerShell** e execute o seguinte comando para clonar o repositório e entrar na pasta:

   ```
   git clone https://github.com/Jhessye/sistema-estoque.git && cd sistema-estoque
   ```

2. Certifique-se de que o **Docker Desktop** está instalado e em execução.

3. Inicie o banco de dados PostgreSQL via Docker Compose (modo *detached*):

   ```
   docker compose -f banco_de_dados/docker-compose.yaml up -d
   ```

4. Acesse a pasta `run`:

   ```
   cd run
   ```

5. Execute o arquivo com **.\Iniciar_Windows.bat** 
   esse script iniciará automaticamente o sistema e conectará ao banco de dados que está rodando em Docker.

Pronto! O programa irá aparecer na tela.

---

## Estrutura do Projeto

### Banco de Dados
- **[inserts/](banco_de_dados/Inserts)**
  - [`create.sql`](banco_de_dados/Inserts/create.sql): Script de **criação das tabelas**.
  - [`insert.sql`](banco_de_dados/Inserts/insert.sql): Script de **inserção de dados fictícios**.
- **[postgres-init/](banco_de_dados/postgres-init)**  
  - Script unificado de **inicialização do banco**, contendo criação e inserção de dados.
- **[target/](banco_de_dados/target)**  
  - Diretório utilizado pelo PostgreSQL durante a execução do container.

---

### Diagramas
- **[diagrams/](diagrams)**  
  Contém:
  - **Diagrama Relacional** do banco de dados.  
  - **Diagrama de Classes** do sistema.
  
  As entidades representadas são:
  - `CATEGORIA`
  - `PRODUTO`
  - `MOVIMENTACOES`

---

### Run

- Contém os executáveis do sistema, prontos para uso após a compilação. Essa pasta permite iniciar o sistema rapidamente, sem precisar abrir o NetBeans.

  | Arquivo                                            | Descrição                                                                                       |
  | -------------------------------------------------- | ----------------------------------------------------------------------------------------------- |
  | **Iniciar_Linux.sh**                               | Script de inicialização para Linux. Executa o `.jar` e conecta automaticamente ao banco Docker. |
  | **Iniciar_Windows.bat**                            | Script de inicialização para Windows, responsável por abrir o sistema de forma automática.      |
  | **projetoEstoque-1.0.0-jar-with-dependencies.jar** | Arquivo `.jar` compilado com todas as dependências necessárias para execução.                   |

- Essa pasta serve como ponto central para executar o sistema em qualquer ambiente, desde que o Docker esteja instalado e ativo.
---

### Código-Fonte
- **[src/main/java/](src/main/java)**  
  Estrutura principal do projeto Java:

  | Diretório | Descrição |
  |------------|------------|
  | **conexion/** | Contém o módulo de conexão com o banco de dados → [`ModuloConexao.java`](src/main/java/conexion/ModuloConexao.java) |
  | **controller/** | Classes **controladoras**, responsáveis pelas operações de inserção, alteração e exclusão. |
  | **model/** | Classes de **entidades** (baseadas no [diagrama relacional](diagrams/DIAGRAMA_RELACIONAL_PEDIDOS.pdf)). |
  | **reports/** | Contém a classe [`Relatorios.java`](src/main/java/reports/Relatorios.java), responsável pela **geração de relatórios** do sistema. |
  | **sql/** | Scripts SQL utilizados para **geração dos relatórios**. |
  | **persisted/** | Classes responsáveis pela **manipulação e tratamento de dados**, conectando as entidades ao banco. |
  | **principal/** | Contém a **tela principal do sistema** (menu inicial). |
  | **view/** | Interfaces gráficas (CRUDs), desenvolvidas com o **NetBeans GUI Builder**. |

---

## Bibliotecas Utilizadas

- **`java.util.LinkedList`**  
  Utilizada para armazenar e manipular listas dinâmicas de dados de forma eficiente.
  
- **`java.sql.SQLException`**  
  Responsável por tratar **exceções SQL**, garantindo a estabilidade do sistema.
  
- **`javax.swing.JOptionPane`**  
  Usada para criar **pop-ups interativos** (confirmações, mensagens e avisos) nas interfaces gráficas.

---

## Contato

- [LinkedIn](https://www.linkedin.com/in/jhessye-lorrayne-924733243/)  
- [E-mail](mailto:ljhessye@gmail.com)

















