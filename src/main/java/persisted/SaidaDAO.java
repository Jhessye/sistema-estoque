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
import model.Saida;

/**
 *
 * @author Jhessye Lorrayne
 */
public class SaidaDAO {
    
    private LinkedList<Saida> listaSaida = new LinkedList<>();
    
    public SaidaDAO() throws SQLException {
        carregarListaSaida();
    }
    
    private void carregarListaSaida() throws SQLException {
        listaSaida.clear();
        String sql = "SELECT * FROM movimentacoes";
        
        try (Connection conector = ModuloConexao.conector();
             PreparedStatement executa = conector.prepareStatement(sql);
             ResultSet rs = executa.executeQuery()) {
            
            while (rs.next()) {
                Saida s = new Saida();
                s.setIdMovimentacao(rs.getInt("id_movimentacoes"));
                s.setTipo(rs.getString("tipo"));
                s.setQuantidade(rs.getInt("quantidade"));
                s.setData(rs.getDate("data").toString());
                s.setValor(rs.getDouble("valor"));
                s.setIdProduto(rs.getInt("id_produto"));
                listaSaida.add(s);
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar movimentações");
        }
    }
    
    public boolean inserirSaida(Saida saida) {
        String sql = "INSERT INTO movimentacoes (tipo, quantidade, data, valor) VALUES (?,?,?,?)";

        try (Connection conector = ModuloConexao.conector();
             PreparedStatement executa = conector.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            executa.setString(1, saida.getTipo());
            executa.setInt(2, saida.getQuantidade());
            executa.setDate(3, java.sql.Date.valueOf(saida.getData()));
            executa.setDouble(4, saida.getValor());

            int linhasAfetadas = executa.executeUpdate();

            if (linhasAfetadas > 0) {
                try (ResultSet rs = executa.getGeneratedKeys()) {
                    if (rs.next()) {
                        saida.setIdMovimentacao(rs.getInt(1));
                        listaSaida.add(saida);
                        return true;
                    }
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return false;
    }
    
    public boolean atualizarSaida(Saida saida, String atributo) {
        String sql = null;

        switch (atributo) {
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

            switch (atributo) {
                case "tipo" -> executa.setString(1, saida.getTipo());
                case "quantidade" -> executa.setInt(1, saida.getQuantidade());
                case "data" -> executa.setDate(1, java.sql.Date.valueOf(saida.getData()));
                case "valor" -> executa.setDouble(1, saida.getValor());
            }

            executa.setInt(2, saida.getIdMovimentacao());

            int linhas = executa.executeUpdate();

            if (linhas > 0) {
                atualizarNaLista(saida);
                return true;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar movimentação: " + e.getMessage());
        }
        return false;
    }

    private void atualizarNaLista(Saida saida) {
        for (int i = 0; i < listaSaida.size(); i++) {
            if (listaSaida.get(i).getIdMovimentacao() == saida.getIdMovimentacao()) {
                listaSaida.set(i, saida);
                break;
            }
        }
    }
    
    public boolean removerSaida(Saida saida) {
        String sql = "DELETE FROM movimentacoes WHERE id_movimentacoes=?";

        try (Connection con = ModuloConexao.conector();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, saida.getIdMovimentacao());

            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                listaSaida.remove(saida);
                return true;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao remover saida: " + e.getMessage());
        }
        return false;
    }
    
    public LinkedList<Saida> verSaidaLista() {
        return new LinkedList<>(listaSaida);
    }
    
    public LinkedList<Saida> verSaidaSQL() throws SQLException {
        LinkedList<Saida> saidas = new LinkedList<>();
        String sql = "SELECT * FROM movimentacoes WHERE tipo = 'saida'";
        
        try (Connection conector = ModuloConexao.conector();
             PreparedStatement executa = conector.prepareStatement(sql);
             ResultSet rs = executa.executeQuery()) {
            
            while (rs.next()) {
                Saida s = new Saida();
                s.setIdMovimentacao(rs.getInt("id_movimentacoes"));
                s.setTipo(rs.getString("tipo"));
                s.setQuantidade(rs.getInt("quantidade"));
                s.setData(rs.getDate("data").toString());
                s.setValor(rs.getDouble("valor"));
                s.setIdProduto(rs.getInt("id_produto"));
                saidas.add(s);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar saídas: " + e.getMessage());
            throw e;
        }
        
        return saidas;
    }
}
