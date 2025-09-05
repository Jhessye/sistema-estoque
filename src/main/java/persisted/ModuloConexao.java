package persisted;

import java.sql.*;

public class ModuloConexao {
    
    // Configurações de conexão (podem ser movidas para um arquivo de properties depois)
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://localhost:5432/estocar?sslmode=disable";
    private static final String USER = "estocar_user";
    private static final String PASSWORD = "123321";
    
    public static Connection conector() {
        Connection conexao = null;
        
        System.out.println("🔧 Iniciando tentativa de conexão...");
        System.out.println("📋 Configurações:");
        System.out.println("   URL: " + URL);
        System.out.println("   Usuário: " + USER);
        
        try {
            // 1. Carregar o driver JDBC
            System.out.println("📦 Carregando driver: " + DRIVER);
            Class.forName(DRIVER);
            System.out.println("✅ Driver carregado com sucesso");
            
            // 2. Estabelecer conexão
            System.out.println("🔗 Conectando ao banco de dados...");
            conexao = DriverManager.getConnection(URL, USER, PASSWORD);
            
            // 3. Verificar se a conexão foi bem-sucedida
            if (conexao != null && !conexao.isClosed()) {
                System.out.println("✅ Conexão estabelecida com sucesso!");
                
                // Mostrar informações do banco
                DatabaseMetaData metaData = conexao.getMetaData();
                System.out.println("📊 Banco de dados: " + metaData.getDatabaseProductName());
                System.out.println("🔢 Versão: " + metaData.getDatabaseProductVersion());
                System.out.println("🏷️  Nome do banco: estocar");
                
                return conexao;
            } else {
                System.out.println("❌ Falha na conexão: conexão nula ou fechada");
                return null;
            }
            
        } catch (ClassNotFoundException e) {
            System.err.println("❌ ERRO: Driver PostgreSQL não encontrado!");
            System.err.println("   Verifique se a dependência está no pom.xml:");
            System.err.println("   <dependency>");
            System.err.println("     <groupId>org.postgresql</groupId>");
            System.err.println("     <artifactId>postgresql</artifactId>");
            System.err.println("     <version>42.6.0</version>");
            System.err.println("   </dependency>");
            e.printStackTrace();
            return null;
            
        } catch (SQLException e) {
            System.err.println("❌ ERRO na conexão com o banco de dados:");
            System.err.println("   Código do erro: " + e.getErrorCode());
            System.err.println("   Estado SQL: " + e.getSQLState());
            System.err.println("   Mensagem: " + e.getMessage());
            
            // Verificação específica de problemas comuns
            if (e.getMessage().contains("authentication failed")) {
                System.err.println("   👤 Problema de autenticação - verifique usuário e senha");
            } else if (e.getMessage().contains("does not exist")) {
                System.err.println("   🗄️  Banco de dados 'estocar' não encontrado");
            } else if (e.getMessage().contains("Connection refused")) {
                System.err.println("   🔌 Conexão recusada - verifique se o PostgreSQL está rodando");
                System.err.println("   💡 Execute no terminal: pg_isready -h localhost -p 5432");
            }
            
            return null;
        } catch (Exception e) {
            System.err.println("❌ ERRO inesperado: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    // Método para testar a conexão
    public static void testarConexao() {
        System.out.println("🧪 Iniciando teste de conexão...");
        
        try (Connection conn = conector()) {
            if (conn != null) {
                System.out.println("🎉 Teste de conexão BEM-SUCEDIDO!");
                
                // Testar uma consulta simples
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT version(), current_timestamp, current_database()");
                
                if (rs.next()) {
                    System.out.println("🐘 Versão PostgreSQL: " + rs.getString(1));
                    System.out.println("⏰ Data/hora do servidor: " + rs.getTimestamp(2));
                    System.out.println("🗃️  Banco conectado: " + rs.getString(3));
                }
                
                rs.close();
                stmt.close();
            } else {
                System.out.println("💥 Teste de conexão FALHOU!");
            }
        } catch (SQLException e) {
            System.err.println("❌ Erro durante o teste: " + e.getMessage());
        }
    }
    
    // Método para fechar a conexão
    public static void fecharConexao(Connection conexao) {
        if (conexao != null) {
            try {
                if (!conexao.isClosed()) {
                    conexao.close();
                    System.out.println("🔌 Conexão fechada com sucesso");
                }
            } catch (SQLException e) {
                System.err.println("❌ Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
    
    // Main para testar diretamente
    public static void main(String[] args) {
        testarConexao();
    }
}