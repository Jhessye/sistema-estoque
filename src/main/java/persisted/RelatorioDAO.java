package persisted;

import conexion.ModuloConexao;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Classe responsável por gerar relatórios a partir de queries SQL internas.
 * 
 * Compatível com execução no NetBeans e via arquivo .jar (Windows/Linux).
 * 
 * @author Jhessye
 */
public class RelatorioDAO {

    /**
     * Relatório: movimentações totais por produto.
     */
    public List<Object[]> relatorioMovimentacaoPorProduto() {
        List<Object[]> resultados = new LinkedList<>();

        // Query SQL diretamente no código
        String sql = """
            SELECT p.nome AS Produto,
                   SUM(m.valor) AS TotalMovimentado
            FROM movimentacoes m
            JOIN produtos p ON p.id_produto = m.id_produto
            GROUP BY p.nome;
        """;

        try (Connection con = ModuloConexao.conector();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Object[] linha = {
                    rs.getString("Produto"),
                    rs.getDouble("TotalMovimentado")
                };
                resultados.add(linha);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao executar relatório: " + e.getMessage());
        }

        return resultados;
    }

    /**
     * Relatório: movimentações agrupadas por categoria de produto.
     */
    public List<Object[]> relatorioMovimentacoesCategoriaProduto() {
        List<Object[]> resultados = new LinkedList<>();

        // Query SQL diretamente no código
        String sql = """
            SELECT 
                m.id_movimentacoes AS idMovimentacoes,
                m.data,
                p.id_produto AS idProduto,
                p.nome AS produtos,
                p.quantidade,
                p.preco AS valor,
                c.id_categoria AS idCategoria,
                c.nome AS categoria
            FROM movimentacoes m
            JOIN produtos p ON p.id_produto = m.id_produto
            JOIN categorias c ON c.id_categoria = p.id_categoria;
        """;

        try (Connection con = ModuloConexao.conector();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Object[] linha = {
                    rs.getInt("idMovimentacoes"),
                    rs.getDate("data"),
                    rs.getString("produtos"),
                    rs.getInt("quantidade"),
                    rs.getDouble("valor"),
                    rs.getString("categoria")
                };
                resultados.add(linha);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao executar relatório: " + e.getMessage());
        }

        return resultados;
    }
}
