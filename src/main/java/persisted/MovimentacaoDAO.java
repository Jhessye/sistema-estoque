package persisted;

import conexion.ModuloConexao;
import java.sql.*;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import model.Movimentacao;
import model.Produto;

/**
 *
 * @author Jhessye Lorrayne
 */
public class MovimentacaoDAO {

    private LinkedList<Movimentacao> listaMovimentacao = new LinkedList<>();

    public MovimentacaoDAO() throws SQLException {
        carregarListaMovimentacao();
    }

    private void carregarListaMovimentacao() throws SQLException {
        listaMovimentacao.clear();
        String sql = "SELECT * FROM movimentacoes";

        try (Connection conector = ModuloConexao.conector();
             PreparedStatement executa = conector.prepareStatement(sql);
             ResultSet rs = executa.executeQuery()) {

            while (rs.next()) {
                Movimentacao m = new Movimentacao() {
                    @Override
                    public boolean movimenta(Produto produto) {
                        return true;
                    }
                };

                Produto p = new Produto();
                p.setIdProduto(rs.getInt("id_produto"));
                p.setQuantidade(rs.getInt("quantidade"));
                p.setPreco(rs.getDouble("valor"));

                m.setIdMovimentacao(rs.getInt("id_movimentacoes"));
                m.setProduto(p);
                m.setData(rs.getDate("data").toString());

                listaMovimentacao.add(m);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar movimentações: " + e.getMessage());
            throw e;
        }
    }

    
    public boolean inserirMovimentacao(Movimentacao movimentacao) {
        String sql = "INSERT INTO movimentacoes (quantidade, data, valor, id_produto) VALUES (?,?,?,?)";

        try (Connection conector = ModuloConexao.conector();
             PreparedStatement executa = conector.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            executa.setInt(1, movimentacao.getProduto().getQuantidade());
            executa.setDate(2, java.sql.Date.valueOf(movimentacao.getData()));
            executa.setDouble(3, movimentacao.getProduto().getPreco());
            executa.setInt(4, movimentacao.getProduto().getIdProduto());

            int linhasAfetadas = executa.executeUpdate();

            if (linhasAfetadas > 0) {
                try (ResultSet rs = executa.getGeneratedKeys()) {
                    if (rs.next()) {
                        movimentacao.setIdMovimentacao(rs.getInt(1)); //pega o id
                        listaMovimentacao.add(movimentacao);
                        return true;
                    }
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir movimentação: " + e.getMessage());
        }
        return false;
    }

    
    public boolean atualizarMovimentacao(Movimentacao movimentacao, String atributo) {
        String sql = null;

        switch (atributo) {
            case "quantidade" -> sql = "UPDATE movimentacoes SET quantidade=? WHERE id_movimentacoes=?";
            case "data" -> sql = "UPDATE movimentacoes SET data=? WHERE id_movimentacoes=?";
            case "valor" -> sql = "UPDATE movimentacoes SET valor=? WHERE id_movimentacoes=?";
            case "id_produto" -> sql = "UPDATE movimentacoes SET id_produto=? WHERE id_movimentacoes=?";
            default -> {
                JOptionPane.showMessageDialog(null, "Atributo inválido!");
                return false;
            }
        }

        try (Connection conector = ModuloConexao.conector();
             PreparedStatement executa = conector.prepareStatement(sql)) {

            switch (atributo) {
                case "quantidade" -> executa.setInt(1, movimentacao.getProduto().getQuantidade());
                case "data" -> executa.setDate(1, java.sql.Date.valueOf(movimentacao.getData()));
                case "valor" -> executa.setDouble(1, movimentacao.getProduto().getPreco());
                case "id_produto" -> executa.setInt(1, movimentacao.getProduto().getIdProduto());
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
        for (int i = 0; i < listaMovimentacao.size(); i++) {
            if (listaMovimentacao.get(i).getIdMovimentacao() == movimentacao.getIdMovimentacao()) {
                listaMovimentacao.set(i, movimentacao);
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
                listaMovimentacao.remove(movimentacao);
                return true;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao remover movimentação: " + e.getMessage());
        }
        return false;
    }

    
    public LinkedList<Movimentacao> verMovimentacaoLista() {
        return new LinkedList<>(listaMovimentacao);
    }

    public LinkedList<Movimentacao> verMovimentacaoSQL() throws SQLException {
        LinkedList<Movimentacao> movimentacoes = new LinkedList<>();
        String sql = "SELECT * FROM movimentacoes";

        try (Connection conector = ModuloConexao.conector();
             PreparedStatement executa = conector.prepareStatement(sql);
             ResultSet rs = executa.executeQuery()) {

            while (rs.next()) {
                Movimentacao m = new Movimentacao() {
                    @Override
                    public boolean movimenta(Produto produto) {
                        return true;
                    }
                };

                Produto p = new Produto();
                p.setIdProduto(rs.getInt("id_produto"));
                p.setQuantidade(rs.getInt("quantidade"));
                p.setPreco(rs.getDouble("valor"));

                m.setIdMovimentacao(rs.getInt("id_movimentacoes"));
                m.setProduto(p);
                m.setData(rs.getDate("data").toString());

                movimentacoes.add(m);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar movimentações: " + e.getMessage());
            throw e;
        }

        return movimentacoes;
    }

    //tela inicial
    public int quantidadeRegistrosMovimentacao() throws SQLException {
        int totalLinhas = 0;
        String sql = "SELECT COUNT(*) AS total_linhas FROM movimentacoes";

        try (Connection conector = ModuloConexao.conector();
             PreparedStatement executa = conector.prepareStatement(sql);
             ResultSet rs = executa.executeQuery()) {

            if (rs.next()) {
                totalLinhas = rs.getInt("total_linhas");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao pegar registros de movimentações: " + e.getMessage());
            throw e;
        }

        return totalLinhas;
    }
}