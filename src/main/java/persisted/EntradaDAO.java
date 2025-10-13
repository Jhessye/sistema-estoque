/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persisted;

import conexion.ModuloConexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import model.Entrada;

/**
 *
 * @author Jhessye Lorrayne
 */
public class EntradaDAO {
    
    private LinkedList<Entrada> listaEntrada = new LinkedList<>();
    
    public EntradaDAO() throws SQLException{
        carregarListaEntrada();
    }
    
    private void carregarListaEntrada() throws SQLException{
        listaEntrada.clear();
        String sql = "SELECT * FROM movimentacoes";
        
        try (Connection conector = ModuloConexao.conector();
            PreparedStatement executa = conector.prepareStatement(sql);
            ResultSet rs = executa.executeQuery()){
            
            while (rs.next()) {
                Entrada e = new Entrada();
                e.setIdMovimentacao(rs.getInt("id_movimentacoes"));
                e.setTipo(rs.getString("tipo"));
                e.setQuantidade(rs.getInt("quantidade"));
                e.setData(rs.getDate("data").toString());
                e.setValor(rs.getDouble("valor"));
                e.setIdProduto(rs.getInt("id_produto"));
                listaEntrada.add(e);
            }
            
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar movimentações");
        }
    }
    
    public boolean inserirEntrada(Entrada entrada){
        
        String sql = "INSERT INTO movimentacoes (tipo, quantidade, data, valor) VALUES (?,?,?,?)";

        try (Connection conector = ModuloConexao.conector();
            PreparedStatement executa = conector.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            
            executa.setString(1, entrada.getTipo());
            executa.setInt(2, entrada.getQuantidade());
            executa.setDate(3, java.sql.Date.valueOf(entrada.getData())); //faz a conversão correta do tipo Java moderno (LocalDate) para o tipo que o JDBC entende java.sql.Date
            executa.setDouble(4, entrada.getValor());

            int linhasAfetadas = executa.executeUpdate();

            if (linhasAfetadas > 0) {
                try (ResultSet rs = executa.getGeneratedKeys()) {
                    if (rs.next()) {
                        entrada.setIdMovimentacao(rs.getInt(1));
                        listaEntrada.add(entrada);
                        return true;
                    }
                }
            }

        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return false;
        
    }
    
    public boolean atualizarEntrada(Entrada entrada, String atributo) {
        String sql = null;

        switch(atributo) {
            case "tipo" -> sql = "UPDATE movimentacoes SET tipo=? WHERE id_movimentacoes=?";//entrada
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
                case "tipo" -> executa.setString(1, entrada.getTipo()); //entrada
                case "quantidade" -> executa.setInt(1, entrada.getQuantidade());
                case "data" -> executa.setDate(1, java.sql.Date.valueOf(entrada.getData()));
                case "valor" -> executa.setDouble(1, entrada.getValor());
                
            }

            executa.setInt(2, entrada.getIdMovimentacao());

            int linhas = executa.executeUpdate();

            if (linhas > 0) {
                atualizarNaLista(entrada);
                return true;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar movimentação: " + e.getMessage());
        }
        return false;
    }

    private void atualizarNaLista(Entrada entrada) {
        for (int i = 0; i < listaEntrada.size(); i++) {
            if (listaEntrada.get(i).getIdMovimentacao() == entrada.getIdMovimentacao()) {
                listaEntrada.set(i, entrada);
                break;
            }
        }
    }
    
    public boolean removerEntrada(Entrada entrada) {
        String sql = "DELETE FROM movimentacoes WHERE id_movimentacoes=?";

        try (Connection con = ModuloConexao.conector();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, entrada.getIdMovimentacao());

            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                listaEntrada.remove(entrada);
                return true;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao remover movimentação: " + e.getMessage());
        }
        return false;
    }
    
    public LinkedList<Entrada> verEntradaLista() {
        return new LinkedList<>(listaEntrada);
    }
    
    public LinkedList<Entrada> verEntradaSQL() throws SQLException{
        LinkedList<Entrada> entradas = new LinkedList<>();
        String sql = "SELECT * FROM movimentacoes WHERE tipo = 'entrada'";
        
        try (Connection conector = ModuloConexao.conector();
             PreparedStatement executa = conector.prepareStatement(sql);
             ResultSet rs = executa.executeQuery()) {
            
            while (rs.next()) {
                Entrada e = new Entrada();
                e.setIdMovimentacao(rs.getInt("id_movimentacoes"));
                e.setTipo(rs.getString("tipo"));
                e.setQuantidade(rs.getInt("quantidade"));
                e.setData(rs.getDate("data").toString());
                e.setValor(rs.getDouble("valor"));
                e.setIdProduto(rs.getInt("id_produto"));
                entradas.add(e);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar entradas: " + e.getMessage());
            throw e;
        }
        
        return entradas;
    }
    
}
