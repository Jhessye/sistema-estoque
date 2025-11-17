/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persisted;

import conexion.ModuloConexao;
import model.Categoria;
import model.Produto;
import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Jhessye
 */
public class ProdutoDAO {
    
    private final LinkedList<Produto> listaProdutos = new LinkedList<>();
    
    public ProdutoDAO() throws SQLException {
        carregarLista();
    }
    
    private void carregarLista() throws SQLException {
        listaProdutos.clear();
        String sql = "SELECT * FROM produtos";
        
        try (Connection conector = ModuloConexao.conector();
             PreparedStatement executa = conector.prepareStatement(sql);
             ResultSet rs = executa.executeQuery()) {
            
            while (rs.next()) {
                Produto p = new Produto();
                p.setIdProduto(rs.getInt("id_produto"));
                p.setNome(rs.getString("nome"));
                p.setDescricao(rs.getString("descricao"));
                p.setMarca(rs.getString("marca"));
                p.setQuantidade(rs.getInt("quantidade"));
                p.setPreco(rs.getDouble("preco"));
                
                
                // Cria a categoria apenas com o id (caso queira carregar o nome depois)
                Categoria c = new Categoria();
                c.setIdCategoria(rs.getInt("id_categoria"));
                p.setCategoria(c);
                
                listaProdutos.add(p);
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar produtos: " + e.getMessage());
        }
    }
    
    public boolean inserirProduto(Produto produto) {
        if (listaProdutos.contains(produto)) {
            JOptionPane.showMessageDialog(null, "Esse produto já existe!");
            return false;
        }

        String sql = "INSERT INTO produtos (nome, descricao, marca, quantidade, preco, id_categoria) VALUES (?,?,?,?,?,?)";

        try (Connection conector = ModuloConexao.conector();
             PreparedStatement executa = conector.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            executa.setString(1, produto.getNome());
            executa.setString(2, produto.getDescricao());
            executa.setString(3, produto.getMarca());
            executa.setInt(4, produto.getQuantidade());     
            executa.setDouble(5, produto.getPreco());       
            executa.setInt(6, produto.getCategoria().getIdCategoria());

            int linhasAfetadas = executa.executeUpdate();

            if (linhasAfetadas > 0) {
                try (ResultSet rs = executa.getGeneratedKeys()) {
                    if (rs.next()) {
                        produto.setIdProduto(rs.getInt(1));
                        listaProdutos.add(produto);
                        return true;
                    }
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir produto: " + e.getMessage());
        }

        return false;
    }
    
    public Produto buscarPorId(int idProduto) throws SQLException {
        String sql = "SELECT * FROM produtos WHERE id_produto = ?";
        Produto produto = null;

        try (Connection con = ModuloConexao.conector();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idProduto);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    produto = new Produto();
                    produto.setIdProduto(rs.getInt("id_produto"));
                    produto.setNome(rs.getString("nome"));
                    produto.setDescricao(rs.getString("descricao"));
                    produto.setMarca(rs.getString("marca"));
                    produto.setPreco(rs.getDouble("preco"));
                    produto.setQuantidade(rs.getInt("quantidade"));

                    //
                    CategoriaDAO categoriaDAO = new CategoriaDAO();
                    Categoria categoria = categoriaDAO.buscarPorId(rs.getInt("id_categoria"));
                    produto.setCategoria(categoria);
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar produto por ID: " + e.getMessage());
            throw e;
        }

        return produto;
    }
    
    public boolean atualizarProduto(Produto produto, String atributo) {
        String sql = switch (atributo) {
            case "nome" -> "UPDATE produtos SET nome=? WHERE id_produto=?";
            case "descricao" -> "UPDATE produtos SET descricao=? WHERE id_produto=?";
            case "marca" -> "UPDATE produtos SET marca=? WHERE id_produto=?";
            case "preco" -> "UPDATE produtos SET preco=? WHERE id_produto=?";
            case "quantidade" -> "UPDATE produtos SET quantidade=? WHERE id_produto=?";
            case "id_categoria" -> "UPDATE produtos SET id_categoria=? WHERE id_produto=?";
            default -> null;
        };

        if (sql == null) {
            JOptionPane.showMessageDialog(null, "Atributo inválido!");
            return false;
        }

        try (Connection conector = ModuloConexao.conector();
             PreparedStatement executa = conector.prepareStatement(sql)) {

            switch (atributo) {
                case "nome" -> executa.setString(1, produto.getNome());
                case "descricao" -> executa.setString(1, produto.getDescricao());
                case "marca" -> executa.setString(1, produto.getMarca());
                case "quantidade" -> executa.setInt(1, produto.getQuantidade());
                case "preco" -> executa.setDouble(1, produto.getPreco());
                case "id_categoria" -> executa.setInt(1, produto.getCategoria().getIdCategoria());
            }

            executa.setInt(2, produto.getIdProduto());

            int linhas = executa.executeUpdate();
            if (linhas > 0) {
                atualizarNaLista(produto);
                return true;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar produto: " + e.getMessage());
        }

        return false;
    }

    private void atualizarNaLista(Produto produto) {
        for (int i = 0; i < listaProdutos.size(); i++) {
            if (listaProdutos.get(i).getIdProduto() == produto.getIdProduto()) {
                listaProdutos.set(i, produto);
                break;
            }
        }
    }
    
    public boolean removerProduto(Produto produto) {
        String sql = "DELETE FROM produtos WHERE id_produto=?";

        try (Connection conector = ModuloConexao.conector();
             PreparedStatement executa = conector.prepareStatement(sql)) {

            executa.setInt(1, produto.getIdProduto());
            int linhas = executa.executeUpdate();

            if (linhas > 0) {
                listaProdutos.remove(produto);
                return true;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao remover produto: " + e.getMessage());
        }

        return false;
    }
    
    public LinkedList<Produto> verProdutosLista() {
        return new LinkedList<>(listaProdutos);
    }
    
    public LinkedList<Produto> verProdutosSQL() throws SQLException {
        LinkedList<Produto> produtos = new LinkedList<>();
        String sql = "SELECT * FROM produtos";

        try (Connection conector = ModuloConexao.conector();
             PreparedStatement executa = conector.prepareStatement(sql);
             ResultSet rs = executa.executeQuery()) {

            while (rs.next()) {
                Produto p = new Produto();
                p.setIdProduto(rs.getInt("id_produto"));
                p.setNome(rs.getString("nome"));
                p.setDescricao(rs.getString("descricao"));
                p.setMarca(rs.getString("marca"));
                p.setQuantidade(rs.getInt("quantidade"));
                p.setPreco(rs.getDouble("preco"));
                

                Categoria c = new Categoria();
                c.setIdCategoria(rs.getInt("id_categoria"));
                p.setCategoria(c);

                produtos.add(p);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar produtos: " + e.getMessage());
            throw e;
        }

        return produtos;
    }
    
    //tela inicial
    public int quantidadeRegistrosProdutos() throws SQLException {
        int totalLinhas = 0;
        String sql = "SELECT COUNT(*) AS total_linhas FROM produtos";

        try (Connection conector = ModuloConexao.conector();
             PreparedStatement executa = conector.prepareStatement(sql);
             ResultSet rs = executa.executeQuery()) {

            if (rs.next()) {
                totalLinhas = rs.getInt("total_linhas");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao contar produtos: " + e.getMessage());
            throw e;
        }

        return totalLinhas;
    }
}
