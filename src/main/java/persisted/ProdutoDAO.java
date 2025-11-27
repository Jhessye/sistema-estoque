/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persisted;

import conexion.ModuloConexao;
import model.Categoria;
import model.Produto;
import java.sql.SQLException;
import java.util.*;
import javax.swing.JOptionPane;
import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

/**
 *
 * @author Jhessye
 */
public class ProdutoDAO {
    
    private final LinkedList<Produto> listaProdutos = new LinkedList<>();
    
    public ProdutoDAO() throws SQLException {
        try {
            carregarLista();
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }
    
    private void carregarLista() {
        listaProdutos.clear();
        MongoCollection<Document> col = ModuloConexao.getCollection("produtos");
        for (Document d : col.find()) {
            Produto p = new Produto();
            p.setIdProduto(d.getInteger("id_produto", 0));
            p.setNome(d.getString("nome"));
            p.setDescricao(d.getString("descricao"));
            p.setMarca(d.getString("marca"));
            p.setQuantidade(d.getInteger("quantidade", 0));
            Object precoObj = d.get("preco");
            double precoVal = 0.0;
            if (precoObj instanceof Number) {
                precoVal = ((Number) precoObj).doubleValue();
            }
            p.setPreco(precoVal);

            Categoria c = new Categoria();
            c.setIdCategoria(d.getInteger("id_categoria", 0));
            p.setCategoria(c);

            listaProdutos.add(p);
        }
    }
    
    public boolean inserirProduto(Produto produto) {
        try {
            MongoCollection<Document> col = ModuloConexao.getCollection("produtos");
            Document found = col.find(Filters.eq("nome", produto.getNome())).first();
            if (found != null) {
                JOptionPane.showMessageDialog(null, "Esse produto já existe!");
                return false;
            }

            int nextId = ModuloConexao.getNextSequence("produtos");
            Document d = new Document("id_produto", nextId)
                    .append("nome", produto.getNome())
                    .append("descricao", produto.getDescricao())
                    .append("marca", produto.getMarca())
                    .append("quantidade", produto.getQuantidade())
                    .append("preco", produto.getPreco())
                    .append("id_categoria", produto.getCategoria() == null ? 0 : produto.getCategoria().getIdCategoria());

            col.insertOne(d);
            produto.setIdProduto(nextId);
            listaProdutos.add(produto);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir produto: " + e.getMessage());
            return false;
        }
    }
    
    public Produto buscarPorId(int idProduto) throws SQLException {
        try {
            MongoCollection<Document> col = ModuloConexao.getCollection("produtos");
            Document d = col.find(Filters.eq("id_produto", idProduto)).first();
            if (d == null) return null;
            Produto produto = new Produto();
            produto.setIdProduto(d.getInteger("id_produto", 0));
            produto.setNome(d.getString("nome"));
            produto.setDescricao(d.getString("descricao"));
            produto.setMarca(d.getString("marca"));
            Object precoObj = d.get("preco");
            double precoVal = 0.0;
            if (precoObj instanceof Number) {
                precoVal = ((Number) precoObj).doubleValue();
            }
            produto.setPreco(precoVal);
            produto.setQuantidade(d.getInteger("quantidade", 0));

            CategoriaDAO categoriaDAO = new CategoriaDAO();
            Categoria categoria = categoriaDAO.buscarPorId(d.getInteger("id_categoria", 0));
            produto.setCategoria(categoria);

            return produto;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar produto por ID: " + e.getMessage());
            throw new SQLException(e);
        }
    }
    
    public boolean atualizarProduto(Produto produto, String atributo) {
        try {
            MongoCollection<Document> col = ModuloConexao.getCollection("produtos");
            switch (atributo) {
                case "nome" -> col.updateOne(Filters.eq("id_produto", produto.getIdProduto()), Updates.set("nome", produto.getNome()));
                case "descricao" -> col.updateOne(Filters.eq("id_produto", produto.getIdProduto()), Updates.set("descricao", produto.getDescricao()));
                case "marca" -> col.updateOne(Filters.eq("id_produto", produto.getIdProduto()), Updates.set("marca", produto.getMarca()));
                case "preco" -> col.updateOne(Filters.eq("id_produto", produto.getIdProduto()), Updates.set("preco", produto.getPreco()));
                case "quantidade" -> col.updateOne(Filters.eq("id_produto", produto.getIdProduto()), Updates.set("quantidade", produto.getQuantidade()));
                case "id_categoria" -> col.updateOne(Filters.eq("id_produto", produto.getIdProduto()), Updates.set("id_categoria", produto.getCategoria().getIdCategoria()));
                default -> {
                    JOptionPane.showMessageDialog(null, "Atributo inválido!");
                    return false;
                }
            }
            carregarLista();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar produto: " + e.getMessage());
            return false;
        }
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
        try {
            MongoCollection<Document> col = ModuloConexao.getCollection("produtos");
            col.deleteOne(Filters.eq("id_produto", produto.getIdProduto()));
            listaProdutos.remove(produto);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao remover produto: " + e.getMessage());
            return false;
        }
    }
    
    public LinkedList<Produto> verProdutosLista() {
        return new LinkedList<>(listaProdutos);
    }
    
    public LinkedList<Produto> verProdutosSQL() throws SQLException {
        try {
            LinkedList<Produto> produtos = new LinkedList<>();
            MongoCollection<Document> col = ModuloConexao.getCollection("produtos");
            for (Document d : col.find()) {
                Produto p = new Produto();
                p.setIdProduto(d.getInteger("id_produto", 0));
                p.setNome(d.getString("nome"));
                p.setDescricao(d.getString("descricao"));
                p.setMarca(d.getString("marca"));
                p.setQuantidade(d.getInteger("quantidade", 0));
                Object precoObj = d.get("preco");
                double precoVal = 0.0;
                if (precoObj instanceof Number) {
                    precoVal = ((Number) precoObj).doubleValue();
                }
                p.setPreco(precoVal);

                Categoria c = new Categoria();
                c.setIdCategoria(d.getInteger("id_categoria", 0));
                p.setCategoria(c);

                produtos.add(p);
            }
            return produtos;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar produtos: " + e.getMessage());
            throw new SQLException(e);
        }
    }
    
    //tela inicial
    public int quantidadeRegistrosProdutos() throws SQLException {
        try {
            MongoCollection<Document> col = ModuloConexao.getCollection("produtos");
            return (int) col.countDocuments();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao contar produtos: " + e.getMessage());
            throw new SQLException(e);
        }
    }
}
