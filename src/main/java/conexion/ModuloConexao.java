package conexion;

import java.sql.*;

public class ModuloConexao {
    
    public static Connection conector(){
        
        java.sql.Connection conexao = null;
        
        String driver = "org.postgresql.Driver"; //chama o driver
        String url = "jdbc:postgresql://localhost:5432/estocar?sslmode=disable";
        String user = "estocar_user";
        String password = "123321";
        
        try {
            
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            System.out.println("PARABENS");
            return conexao;
            
        } catch (ClassNotFoundException e) {
            System.err.println("erro DRIVER");
            e.printStackTrace();
            return null;
            
        } catch (SQLException e) {
            System.err.println("erro CONEXAO BANCO");
            e.printStackTrace();
            return null;
        }
        
    }
}