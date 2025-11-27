package persisted;

import conexion.ModuloConexao;
import model.Categoria;
import java.sql.SQLException;
import java.util.*;
import javax.swing.JOptionPane;
import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

public class CategoriaDAO {

    private LinkedList<Categoria> listaCategorias = new LinkedList<>();

    public CategoriaDAO() throws SQLException {
        try {
            carregarLista();
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    private void carregarLista() {
        listaCategorias.clear();
        MongoCollection<Document> col = ModuloConexao.getCollection("categorias");
        for (Document d : col.find()) {
            Categoria c = new Categoria();
            c.setIdCategoria(d.getInteger("id_categoria", 0));
            c.setNome(d.getString("nome"));
            c.setDescricao(d.getString("descricao"));
            listaCategorias.add(c);
        }
    }

    public boolean inserirCategoria(Categoria categoria) {
        try {
            // Checa existência por nome
            MongoCollection<Document> col = ModuloConexao.getCollection("categorias");
            Document found = col.find(Filters.eq("nome", categoria.getNome())).first();
            if (found != null) {
                JOptionPane.showMessageDialog(null, "Essa categoria já existe");
                return false;
            }

            int nextId = ModuloConexao.getNextSequence("categorias");
            Document d = new Document("id_categoria", nextId)
                    .append("nome", categoria.getNome())
                    .append("descricao", categoria.getDescricao());

            col.insertOne(d);
            categoria.setIdCategoria(nextId);
            listaCategorias.add(categoria);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir categoria: " + e.getMessage());
            return false;
        }
    }

    public Categoria buscarPorId(int idCategoria) throws SQLException {
        try {
            MongoCollection<Document> col = ModuloConexao.getCollection("categorias");
            Document d = col.find(Filters.eq("id_categoria", idCategoria)).first();
            if (d == null) return null;
            Categoria c = new Categoria();
            c.setIdCategoria(d.getInteger("id_categoria", 0));
            c.setNome(d.getString("nome"));
            c.setDescricao(d.getString("descricao"));
            return c;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar categoria por ID: " + e.getMessage());
            throw new SQLException(e);
        }
    }

    public boolean atualizarCategoria(Categoria categoria, String atributo) {
        try {
            MongoCollection<Document> col = ModuloConexao.getCollection("categorias");
            switch (atributo) {
                case "nome" -> {
                    col.updateOne(Filters.eq("id_categoria", categoria.getIdCategoria()), Updates.set("nome", categoria.getNome()));
                }
                case "descricao" -> {
                    col.updateOne(Filters.eq("id_categoria", categoria.getIdCategoria()), Updates.set("descricao", categoria.getDescricao()));
                }
                default -> {
                    JOptionPane.showMessageDialog(null, "Atributo inválido!");
                    return false;
                }
            }
            atualizarNaLista(categoria);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar a categoria: " + e.getMessage());
            return false;
        }
    }

    private void atualizarNaLista(Categoria categoria) {
        for (int i = 0; i < listaCategorias.size(); i++) {
            if (listaCategorias.get(i).getIdCategoria() == categoria.getIdCategoria()) {
                listaCategorias.set(i, categoria);
                break;
            }
        }
    }

    public boolean removerCategoria(Categoria categoria) {
        try {
            MongoCollection<Document> col = ModuloConexao.getCollection("categorias");
            col.deleteOne(Filters.eq("id_categoria", categoria.getIdCategoria()));
            listaCategorias.remove(categoria);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao remover categoria: " + e.getMessage());
            return false;
        }
    }

    public LinkedList<Categoria> verCategoriasLista() throws SQLException {
        try {
            carregarLista();
            return new LinkedList<>(listaCategorias);
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    public LinkedList<Categoria> verCategoriasSQL() throws SQLException {
        try {
            LinkedList<Categoria> categorias = new LinkedList<>();
            MongoCollection<Document> col = ModuloConexao.getCollection("categorias");
            for (Document d : col.find()) {
                Categoria c = new Categoria();
                c.setIdCategoria(d.getInteger("id_categoria", 0));
                c.setNome(d.getString("nome"));
                c.setDescricao(d.getString("descricao"));
                categorias.add(c);
            }
            return categorias;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar categorias: " + e.getMessage());
            throw new SQLException(e);
        }
    }

    public int quantidadeRegistrosCategoria() throws SQLException {
        try {
            MongoCollection<Document> col = ModuloConexao.getCollection("categorias");
            return (int) col.countDocuments();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao pegar registros de categorias: " + e.getMessage());
            throw new SQLException(e);
        }
    }

}
