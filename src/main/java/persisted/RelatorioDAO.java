package persisted;

import conexion.ModuloConexao;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Jhessye
 */
public class RelatorioDAO {

    private String lerArquivoSQL(String caminhoArquivo) throws IOException { //le os sql
        return new String(Files.readAllBytes(Paths.get(caminhoArquivo)));
    }

    public List<Object[]> relatorioMovimentacaoPorProduto() {
        List<Object[]> resultados = new LinkedList<>(); //não tem tipo definido
        String caminho = "sql/relatorioMovimentacaoPorProduto.sql";

        try (Connection con = ModuloConexao.conector()) {
            String sql = lerArquivoSQL(caminho);
            try (PreparedStatement stmt = con.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Object[] linha = {
                        rs.getString("Produto"),
                        rs.getDouble("TotalMovimentado")
                    };
                    resultados.add(linha);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao ler arquivo SQL: " + e.getMessage());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao executar relatório: " + e.getMessage());
        }

        return resultados;
    }

    
    public List<Object[]> relatorioMovimentacoesCategoriaProduto() {
        List<Object[]> resultados = new LinkedList<>(); //não tem tipo definido
        String caminho = "sql/relatorioMovimentacoesCategoriaProduto.sql";

        try (Connection con = ModuloConexao.conector()) {
            String sql = lerArquivoSQL(caminho);
            try (PreparedStatement stmt = con.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Object[] linha = {
                        rs.getInt("idMovimentacao"),
                        rs.getDate("data"),
                        rs.getString("produto"),
                        rs.getInt("quantidade"),
                        rs.getDouble("valor"),
                        rs.getString("categoria")
                    };
                    resultados.add(linha);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao ler arquivo SQL: " + e.getMessage());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao executar relatório: " + e.getMessage());
        }

        return resultados;
    }
}
