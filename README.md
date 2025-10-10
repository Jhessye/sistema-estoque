# Sistema de Gerenciamento de Estoque

Esse sistema é composto por um conjunto de tabelas que representam movimentações de produtos em uma loja de manutenção de automovéis, contendo as seguintes tabelas: categoria, produto e movimentações.
Integrado com banco de dados PostgreSQL rodando em um Docker. Feito em Java usando Netbeans.

# Executando o projeto no Linux
README.MD explicando como executar o projeto no ambiente proposto (LINUX)

# Organização
- sql
  - Códigos para [criação das tabelas](Inserts/create.sql) e [inserção de dados](Inserts/insert.sql) utilizados pelo banco de dados (todos fictícios).
- [diagrams](diagrams)
  - Nesse diretório está o [diagrama relacional](diagrams/Diagrama Relacional - banco de dados.pdf) do sistema e também o [diagrama de Classe](diagrams/Diagrama de Classe.png).
    * Existem cinco entidades: CATEGORIA, PRODUTO e MOVIMENTACOES.
- [src/main/java](src/main/java)
  - Contém todo o Script do projeto, telas e a conexão com o banco.
    * [conexion](src/main/java/conexion): Aqui está o [módulo de conexão](src/main/java/conexion/ModuloConexao.java) com o banco de dados.
      - inserts
      - target
      - postgres-init
    * [controller](src/main/java/controller): Aqui, temos as classes controladoras, que fazem a inserção, alteração e exclusão dos registros.
    * [model](src/main/java/model): Aqui, temos as entidades descritas no [diagrama relacional](diagrams/DIAGRAMA_RELACIONAL_PEDIDOS.pdf) em forma de classes.
    * [reports](src/main/java/reports): Aqui encontamos a [classe](src/main/java/reports/Relatorios.java) responsável por gerar os dois relatórios no sistema.
    * [sql](src/main/java/sql): Aqui, temos os scripts SQL usados na geração dos relatórios.
    * [persisted](src/main/java/persisted): Scripts responsáveis por gerenciar toda a manupilação de dados e tratamentos para a inserção e coleta de informações do banco de dados para cada entidade.
    * [principal](src/main/java/principal): Aqui, temos a tela principal do programa, o menu, onde o programa começa (e sempre retorna).
    * [view](src/main/java/view): Scripts de interface de usuário, todas as telas do CRUD. Feito com o que é disponibilizado pelo Netbeans.

# Bibliotecas Utilizadas
- `java.util.LinkedList`: Foi usado a biblioteca .util do Java para utilização de LinkedLists, que auxiliaram na mostragem e coleta de dados.
- `java.sql.SQLException`: Com essa ferramenta, cuidamos de possíveis erros durante a utilização de comandos SQL no código.
- `javax.swing.JOptionPane`: Auxiliou na criação de popups de continuação (sim/não) em cada interface.

# Contato
- [LinkedIn](https://www.linkedin.com/in/jhessye-lorrayne-924733243/)
- [E-Mail](mailto:ljhessye@gmail.com)


