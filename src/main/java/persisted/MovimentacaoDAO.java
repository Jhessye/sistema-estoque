/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persisted;

import conexion.ModuloConexao;
import model.Movimentacao;
import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;
/**
 *
 * @author Jhessye Lorrayne
 */
public class MovimentacaoDAO {
    
    private LinkedList<Movimentacao> listaMovimentacoes = new LinkedList<>();
    
    public MovimentacaoDAO() throws SQLException{
        carregarLista();
    }
    
    private void carregarLista() throws SQLException{
        listaMovimentacoes.clear();
        String sql = "SELECT * FROM movimentacoes";
        
        try (Connection conector = ModuloConexao.conector();
            PreparedStatement executa = conector.prepareStatement(sql);
            ResultSet rs = executa.executeQuery()){
            
            while (rs.next()) {
                Movimentacao m = new Movimentacao();
                m.setIdMovimentacao(rs.getInt("id_movimentacoes"));
                m.setTipo(rs.getString("tipo"));
                m.setQuantidade(rs.getInt("quantidade"));
                m.setData(rs.getDate("data").toLocalDate());
                m.setValor(rs.getDouble("valor"));
                m.setIdProduto(rs.getInt("id_produto"));
                listaMovimentacoes.add(m);
            }
            
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar movimentações");
        }
    }
    
    public boolean inserirMovimentacao(Movimentacao movimentacao){
        
        String sql = "INSERT INTO movimentacoes (tipo, quantidade, data, valor) VALUES (?,?,?,?)";

        try (Connection conector = ModuloConexao.conector();
            PreparedStatement executa = conector.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            
            //se for saida o valor fica negativo
            
            double valor = movimentacao.getValor();
            if ("saída".equalsIgnoreCase(movimentacao.getTipo())) {
                valor = -Math.abs(valor); //negativo
            }
            
            executa.setString(1, movimentacao.getTipo());
            executa.setInt(2, movimentacao.getQuantidade());
            executa.setDate(3, java.sql.Date.valueOf(movimentacao.getData())); //faz a conversão correta do tipo Java moderno (LocalDate) para o tipo que o JDBC entende java.sql.Date
            executa.setDouble(4, movimentacao.getValor());

            int linhasAfetadas = executa.executeUpdate();

            if (linhasAfetadas > 0) {
                try (ResultSet rs = executa.getGeneratedKeys()) {
                    if (rs.next()) {
                        movimentacao.setIdMovimentacao(rs.getInt(1));
                        listaMovimentacoes.add(movimentacao);
                        return true;
                    }
                }
            }

        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return false;
        
    }
    
    public boolean atualizarMovimentacao(Movimentacao movimentacao, String atributo) {
        String sql = null;

        switch(atributo) {
            case "tipo" -> sql = "UPDATE movimentacoes SET tipo=? WHERE id_movimentacoes=?";
            case "quantidade" -> sql = "UPDATE movimentacoes SET quantidade=? WHERE id_movimentacoes=?";
            case "data" -> sql = "UPDATE movimentacoes SET data=? WHERE id_movimentacoes=?";
            case "valor" -> sql = "UPDATE movimentacoes SET valor=? WHERE id_movimentacoes=?";
            
            default -> {
                JOptionPane.showMessageDialog(null, "Atributo inválido!");
                return false;
            }
        }

        try (Connection conector = ModuloConexao.conector();
             PreparedStatement executa = conector.prepareStatement(sql)) {

            switch(atributo) {
                case "tipo" -> executa.setString(1, movimentacao.getTipo());
                case "quantidade" -> executa.setInt(1, movimentacao.getQuantidade());
                case "data" -> executa.setDate(1, java.sql.Date.valueOf(movimentacao.getData()));
                case "valor" -> executa.setDouble(1, movimentacao.getValor());
                
            }

            executa.setInt(2, movimentacao.getIdMovimentacao());

            int linhas = executa.executeUpdate();

            if (linhas > 0) {
                atualizarNaLista(movimentacao);
                return true;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar movimentação: " + e.getMessage());
        }
        return false;
    }

    private void atualizarNaLista(Movimentacao movimentacao) {
        for (int i = 0; i < listaMovimentacoes.size(); i++) {
            if (listaMovimentacoes.get(i).getIdMovimentacao() == movimentacao.getIdMovimentacao()) {
                listaMovimentacoes.set(i, movimentacao);
                break;
            }
        }
    }
    
    public boolean removerMovimentacao(Movimentacao movimentacao) {
        String sql = "DELETE FROM movimentacoes WHERE id_movimentacoes=?";

        try (Connection con = ModuloConexao.conector();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, movimentacao.getIdMovimentacao());

            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                listaMovimentacoes.remove(movimentacao);
                return true;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao remover movimentação: " + e.getMessage());
        }
        return false;
    }
    
    public LinkedList<Movimentacao> verMovimentacoesLista() {
        return new LinkedList<>(listaMovimentacoes);
    }
    
    public LinkedList<Movimentacao> verMovimentacoesSQL() throws SQLException {
        LinkedList<Movimentacao> movimentacoes = new LinkedList<>();
        String sql = "SELECT * FROM movimentacoes";
        
        try (Connection conector = ModuloConexao.conector();
             PreparedStatement executa = conector.prepareStatement(sql);
             ResultSet rs = executa.executeQuery()) {
            
            while (rs.next()) {
                Movimentacao m = new Movimentacao();
                m.setIdMovimentacao(rs.getInt("id_movimentacoes"));
                m.setTipo(rs.getString("tipo"));
                m.setQuantidade(rs.getInt("quantidade"));
                m.setData(rs.getDate("data").toLocalDate());
                m.setValor(rs.getDouble("valor"));
                m.setIdProduto(rs.getInt("id_produto"));
                movimentacoes.add(m);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar movimentações: " + e.getMessage());
            throw e;
        }
        
        return movimentacoes;
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
