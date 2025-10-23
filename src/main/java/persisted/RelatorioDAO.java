package persisted;

import conexion.ModuloConexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RelatorioDAO {

    public List<Object[]> relatorioMovimentacaoPorProduto() {
        String sql = """
            SELECT p.nome AS produto,
                   SUM(m.valor) AS total_movimentado
            FROM movimentacoes m
            JOIN produtos p ON p.id_produto = m.id_produto
            GROUP BY p.nome
            ORDER BY p.nome
        """;
        List<Object[]> out = new ArrayList<>();
        try (Connection con = ModuloConexao.conector();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                out.add(new Object[]{ rs.getString("produto"), rs.getInt("total_movimentado") });
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return out;
    }

    public List<Object[]> relatorioMovimentacoesDetalhado() {
        String sql = """
            SELECT
                m.id_movimentacoes      AS id_mov,      -- <- nome correto (plural)
                m.data                  AS data_mov,    -- <- ajuste se seu campo for outro (ex: data_mov)
                p.nome                  AS produto,
                p.quantidade,
                m.valor,
                c.nome                  AS categoria
            FROM movimentacoes m
            JOIN produtos   p ON p.id_produto   = m.id_produto
            JOIN categorias c ON c.id_categoria = p.id_categoria
            ORDER BY m.data DESC, m.id_movimentacoes DESC
        """;
        List<Object[]> out = new ArrayList<>();
        try (Connection con = ModuloConexao.conector();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                out.add(new Object[]{
                    rs.getInt("id_mov"),
                    rs.getTimestamp("data_mov"),
                    rs.getString("produto"),
                    rs.getInt("quantidade"),
                    rs.getBigDecimal("valor"),
                    rs.getString("categoria")
                });
            }
        } catch (SQLException e) {
            // logue e retorne lista vazia para não quebrar a tela
            e.printStackTrace();
        }
        return out;
    }
}
