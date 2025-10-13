/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persisted;

import conexion.ModuloConexao;
import model.Movimentacao;
import java.sql.*;
import javax.swing.JOptionPane;
/**
 *
 * @author Jhessye Lorrayne
 */
public class MovimentacaoDAO {
    
    public MovimentacaoDAO(){
    }
    
    public boolean inserirMovimentacao(Movimentacao movimentacao){
        
        if ("entrada".equals(movimentacao.getTipo())){
            
        }
    }
    
    public int quantidadeRegistrosMovimentacao() throws SQLException{
        
        int totalLinhas = 0;
        String sql = "SELECT COUNT(*) AS total_linhas FROM movimentacoes";

        try (Connection conector = ModuloConexao.conector(); 
             PreparedStatement executa = conector.prepareStatement(sql);
             ResultSet rs = executa.executeQuery()) {

            while (rs.next()) {
                totalLinhas = rs.getInt("total_linhas");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao pegar registros de movimentacoes: " + e.getMessage());
            throw e;
        }
        
        return totalLinhas;
    }
}
