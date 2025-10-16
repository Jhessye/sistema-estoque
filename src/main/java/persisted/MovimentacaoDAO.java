package persisted;

import conexion.ModuloConexao;
import java.sql.*;
import java.util.LinkedList;  
import javax.swing.JOptionPane;
import model.Movimentacao;   
import model.Produto;
import model.Entrada;
import model.Saida;

public class MovimentacaoDAO {

    private LinkedList<Movimentacao> listaMovimentacao = new LinkedList<>();
    private ProdutoDAO produtoDAO = new ProdutoDAO(); // precisamos dele para buscar o produto

    public MovimentacaoDAO() throws SQLException {
        carregarListaMovimentacao();
    }

    private void carregarListaMovimentacao() throws SQLException {
        listaMovimentacao.clear();
        String sql = "SELECT id_movimentacoes, data, valor, id_produto FROM movimentacoes";

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

                int idProduto = rs.getInt("id_produto");
                Produto p = produtoDAO.buscarPorId(idProduto);

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
    
    public boolean inserirEntrada(Entrada entrada) {
        String sql = "INSERT INTO movimentacoes (data, valor, id_produto) VALUES (?,?,?)";

        try (Connection conector = ModuloConexao.conector();
             PreparedStatement executa = conector.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            double valorCalculado = entrada.valor(entrada.getProduto());

            executa.setDate(1, java.sql.Date.valueOf(entrada.getData()));
            executa.setDouble(2, valorCalculado);
            executa.setInt(3, entrada.getProduto().getIdProduto());

            int linhasAfetadas = executa.executeUpdate();

            if (linhasAfetadas > 0) {
                try (ResultSet rs = executa.getGeneratedKeys()) {
                    if (rs.next()) {
                        entrada.setIdMovimentacao(rs.getInt(1));
                        listaMovimentacao.add(entrada);
                        return true;
                    }
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir entrada: " + e.getMessage());
        }
        return false;
    }

    public boolean inserirSaida(Saida saida) {
        String sql = "INSERT INTO movimentacoes (data, valor, id_produto) VALUES (?,?,?)";

        try (Connection conector = ModuloConexao.conector();
             PreparedStatement executa = conector.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            double valorCalculado = saida.valor(saida.getProduto());

            executa.setDate(1, java.sql.Date.valueOf(saida.getData()));
            executa.setDouble(2, valorCalculado);
            executa.setInt(3, saida.getProduto().getIdProduto());

            int linhasAfetadas = executa.executeUpdate();

            if (linhasAfetadas > 0) {
                try (ResultSet rs = executa.getGeneratedKeys()) {
                    if (rs.next()) {
                        saida.setIdMovimentacao(rs.getInt(1));
                        listaMovimentacao.add(saida);
                        return true;
                    }
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir saida: " + e.getMessage());
        }
        return false;
    }

    
    public boolean atualizarMovimentacao(Movimentacao movimentacao, String atributo) {
        String sql = switch (atributo) {
            case "data" -> "UPDATE movimentacoes SET data=? WHERE id_movimentacoes=?";
            case "id_produto" -> "UPDATE movimentacoes SET id_produto=? WHERE id_movimentacoes=?";
            default -> null;
        };

        if (sql == null) {
            JOptionPane.showMessageDialog(null, "Atributo inválido!");
            return false;
        }

        try (Connection conector = ModuloConexao.conector();
             PreparedStatement executa = conector.prepareStatement(sql)) {

            switch (atributo) {
                case "data" ->
                    executa.setDate(1, java.sql.Date.valueOf(movimentacao.getData()));
                case "id_produto" ->
                    executa.setInt(1, movimentacao.getProduto().getIdProduto());
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

  
    public LinkedList<Movimentacao> verMovimentacaoSQL() throws SQLException {
        LinkedList<Movimentacao> movimentacoes = new LinkedList<>();
        String sql = "SELECT id_movimentacoes, data, valor, id_produto FROM movimentacoes";

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

                int idProduto = rs.getInt("id_produto");
                Produto p = produtoDAO.buscarPorId(idProduto);

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

    public LinkedList<Movimentacao> verMovimentacaoLista() {
        return new LinkedList<>(listaMovimentacao);//copia da lista
    }

    //tela inical
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