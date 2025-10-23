package persisted;

import conexion.ModuloConexao;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 uuuuuuuu
 Classe responsável por gerar relatórios a partir de queries SQL externas.
 
 Compatível com execução no NetBeans e via arquivo .jar (Windows/Linux).
 
 Os arquivos SQL devem estar em:
 
 src/main/resources/sql/
 
 Exemplo: src/main/resources/sql/relatorioMovimentacaoPorProduto.sql
 
 @author Jhessye
 */
public class RelatorioDAO {

    /**
     Lê o conteúdo de um arquivo SQL a partir do classpath.
     */
    private String lerArquivoSQL(String recurso) throws IOException {
        // garante que o caminho comece com "/"
        if (!recurso.startsWith("/")) {
            recurso = "/" + recurso;
        }

        try (InputStream is = getClass().getResourceAsStream(recurso)) {
            if (is == null) {
                throw new IOException("Arquivo SQL não encontrado no classpath: " + recurso);
            }
            return new String(is.readAllBytes());
        }
    }

    /**
     Relatório: movimentações totais por produto.
     */
    public List<Object[]> relatorioMovimentacaoPorProduto() {
        List<Object[]> resultados = new LinkedList<>();
        String caminho = "/sql/relatorioMovimentacaoPorProduto.sql"; // caminho dentro de resources

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
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao executar relatório: " + e.getMessage());
        }

        return resultados;
    }

    /**
     Relatório: movimentações agrupadas por categoria de produto.
     */
    public List<Object[]> relatorioMovimentacoesCategoriaProduto() {
        List<Object[]> resultados = new LinkedList<>();
        String caminho = "/sql/relatorioMovimentacoesCategoriaProduto.sql"; // caminho dentro de resources

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
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao executar relatório: " + e.getMessage());
        }

        return resultados;
    }
}
