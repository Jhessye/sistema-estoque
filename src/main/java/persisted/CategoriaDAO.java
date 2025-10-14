/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persisted;

import conexion.ModuloConexao;
import model.Categoria;
import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;


public class CategoriaDAO {
    
    private LinkedList<Categoria> listaCategorias = new LinkedList<>();
    
    public CategoriaDAO() throws SQLException{
        carregarLista();
    }
    
    private void carregarLista() throws SQLException{
        listaCategorias.clear();
        String sql = "SELECT * FROM categorias";
        
        try (Connection conector = ModuloConexao.conector(); //conecta com o banco
            PreparedStatement executa = conector.prepareStatement(sql);
            ResultSet rs = executa.executeQuery()){
            
            while (rs.next()) { //pra todos os produtos do banco
                Categoria c = new Categoria();
                c.setIdCategoria(rs.getInt("id_categoria"));
                c.setNome(rs.getString("nome"));
                c.setDescricao(rs.getString("descricao"));
                listaCategorias.add(c);
            }
            
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar categorias");
        }
        
    }
    
    public boolean inserirCategoria(Categoria categoria){
        if (listaCategorias.contains(categoria)){ //verifica se ja existe essa categoria
            JOptionPane.showMessageDialog(null, "Esse produto ja existe");
            return false;
        }else{
            String sql = "INSERT INTO categorias (nome, descricao) VALUES (?,?)";

            try (Connection conector = ModuloConexao.conector(); //conecta com o banco
                PreparedStatement executa = conector.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

                executa.setString(1, categoria.getNome());
                executa.setString(2, categoria.getDescricao());

                int linhasAfetadas = executa.executeUpdate();

                if (linhasAfetadas > 0) { //se ele conseguir executar
                    try (ResultSet rs = executa.getGeneratedKeys()) { //rs vai receber o id gerado
                        if (rs.next()) { //vai no ultimo inserido e ve se tem coisa la, se tiver
                            categoria.setIdCategoria(rs.getInt(1)); // pega o ID gerado e coloca no objeto
                            listaCategorias.add(categoria);
                            return true;
                        }
                    }
                }

            }catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
            return false;
        }
    }
    
    public boolean atualizarCategoria(Categoria categoria, String atributo) {
        String sql = null;

        switch(atributo) {
            case "nome" -> sql = "UPDATE categorias SET nome=? WHERE id_categoria=?";
        
            case "descricao" -> sql = "UPDATE categorias SET descricao=? WHERE id_categoria=?";
            
            default -> {
                JOptionPane.showMessageDialog(null, "Atributo inválido!");
                return false;
            }
        }

        try (Connection conector = ModuloConexao.conector(); //conecta com o banco
             PreparedStatement executa = conector.prepareStatement(sql)) {

            switch(atributo) {
                case "nome" -> executa.setString(1, categoria.getNome());
                
                case "descricao" -> executa.setString(1, categoria.getDescricao());
            }

            //o id do produto
            executa.setInt(2, categoria.getIdCategoria());

            int linhas = executa.executeUpdate(); //executa

            if (linhas > 0) {
                atualizarNaLista(categoria);
                return true;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar a categoria: " + e.getMessage());
        }
        return false;
    }

    //atualizar na lista
    private void atualizarNaLista(Categoria categoria) {
        for (int i = 0; i < listaCategorias.size(); i++) {
            if (listaCategorias.get(i).getIdCategoria() == categoria.getIdCategoria()) { //se o id for igual a do produto atualizado
                listaCategorias.set(i, categoria); //coloca nessa posicao os valores novos
                break;
            }
        }
    }
    
    public boolean removerCategoria (Categoria categoria) {
        String sql = "DELETE FROM categorias WHERE id_categoria=?";

        try (Connection conexao = ModuloConexao.conector();
             PreparedStatement executa = conexao.prepareStatement(sql)) {

            executa.setInt(1, categoria.getIdCategoria());

            int linhas = executa.executeUpdate();
            
            if (linhas > 0) {
                //remove da lista tambm
                listaCategorias.remove(categoria);
                return true;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao remover categoria: " + e.getMessage());
        }
        return false;
    }
    
    public LinkedList<Categoria> verCategoriasLista() throws SQLException {
        //copia
        carregarLista();
        return new LinkedList<Categoria>(listaCategorias);
    }
    
    public LinkedList<Categoria> verCategoriasSQL() throws SQLException { //devolve em forma de lista
        LinkedList<Categoria> categorias = new LinkedList<>();
        String sql = "SELECT * FROM categorias";

        try (Connection conector = ModuloConexao.conector(); 
             PreparedStatement executa = conector.prepareStatement(sql);
             ResultSet rs = executa.executeQuery()) {

            while (rs.next()) {
                Categoria c = new Categoria();
                c.setIdCategoria(rs.getInt("id_categoria"));
                c.setNome(rs.getString("nome"));
                c.setDescricao(rs.getString("descricao"));
                categorias.add(c);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar categorias: " + e.getMessage());
            throw e;
        }

        return categorias;
    }
    
    public int quantidadeRegistrosCategoria() throws SQLException{
        
        int totalLinhas = 0;
        String sql = "SELECT COUNT(*) AS total_linhas FROM categorias";

        try (Connection conector = ModuloConexao.conector(); 
             PreparedStatement executa = conector.prepareStatement(sql);
             ResultSet rs = executa.executeQuery()) {

            while (rs.next()) {
                totalLinhas = rs.getInt("total_linhas");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao pegar registros de categorias: " + e.getMessage());
            throw e;
        }
        
        return totalLinhas;
    }
    
}
