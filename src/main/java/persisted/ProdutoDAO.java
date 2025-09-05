/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persisted;

import model.Produto;
import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;
/**
 *
 * @author Jhessye Lorrayne
 */
public class ProdutoDAO {
    
    private LinkedList<Produto> listaProdutos = new LinkedList<>();
    
    public ProdutoDAO() throws SQLException{
        carregarLista();
    }
    
    private void carregarLista() throws SQLException{
        listaProdutos.clear();
        String sql = "SELECT * FROM produto";
        
        try (Connection conector = ModuloConexao.conector(); //conecta com o banco
            PreparedStatement executa = conector.prepareStatement(sql);
            ResultSet rs = executa.executeQuery()){
            
            while (rs.next()) { //pra todos os produtos do banco
                Produto p = new Produto();
                p.setIdProduto(rs.getInt("id_produto"));
                p.setNome(rs.getString("nome"));
                p.setDescricao(rs.getString("descricao"));
                p.setMarca(rs.getString("marca"));
                p.setPreco(rs.getDouble("preco"));
                listaProdutos.add(p);
            }
            
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar produtos");
        }
        
    }
    
    public boolean inserirProduto(Produto produto){
        if (listaProdutos.contains(produto)){ //verifica se ja existe esse produto
            JOptionPane.showMessageDialog(null, "Esse produto ja existe");
            return false;
        }else{
            String sql = "INSERT INTO produto (nome, descricao, marca, preco) VALUES (?,?,?,?)";

            try (Connection conector = ModuloConexao.conector(); //conecta com o banco
                PreparedStatement executa = conector.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

                executa.setString(1, produto.getNome());
                executa.setString(2, produto.getDescricao());
                executa.setString(3, produto.getMarca());
                executa.setDouble(4, produto.getPreco());

                int linhasAfetadas = executa.executeUpdate();

                if (linhasAfetadas > 0) { //se ele conseguir executar
                    try (ResultSet rs = executa.getGeneratedKeys()) { //rs vai receber o id gerado
                        if (rs.next()) { //vai no ultimo inserido e ve se tem coisa la, se tiver
                            produto.setIdProduto(rs.getInt(1)); // pega o ID gerado e coloca no objeto
                            listaProdutos.add(produto);
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
    
    public boolean atualizarProduto(Produto produto, String atributo) {
        String sql = null;

        switch(atributo) {
            case "nome" -> sql = "UPDATE produto SET nome=? WHERE id_produto=?";
        
            case "descricao" -> sql = "UPDATE produto SET descricao=? WHERE id_produto=?";
            
            case "marca" -> sql = "UPDATE produto SET marca=? WHERE id_produto=?";
            
            case "preco" -> sql = "UPDATE produto SET preco=? WHERE id_produto=?";
            
            default -> {
                JOptionPane.showMessageDialog(null, "Atributo inválido!");
                return false;
            }
        }

        try (Connection conector = ModuloConexao.conector(); //conecta com o banco
             PreparedStatement executa = conector.prepareStatement(sql)) {

            switch(atributo) {
                case "nome" -> executa.setString(1, produto.getNome());
                
                case "descricao" -> executa.setString(1, produto.getDescricao());
                
                case "marca" -> executa.setString(1, produto.getMarca());
                
                case "preco" -> executa.setDouble(1, produto.getPreco());
            }

            //o id do produto
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

    //atualizar na lista
    private void atualizarNaLista(Produto produto) {
        for (int i = 0; i < listaProdutos.size(); i++) {
            if (listaProdutos.get(i).getIdProduto() == produto.getIdProduto()) { //se o id for igual a do produto atualizado
                listaProdutos.set(i, produto); //coloca nessa posicao os valores novos
                break;
            }
        }
    }
    
    public boolean removerProduto(Produto produto) {
        String sql = "DELETE FROM produto WHERE id_produto=?";

        try (Connection conector = ModuloConexao.conector();
             PreparedStatement executa = conector.prepareStatement(sql)) {

            executa.setInt(1, produto.getIdProduto());

            int linhas = executa.executeUpdate();
            if (linhas > 0) {
                //remove da lista tambm
                listaProdutos.remove(produto);
                return true;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao remover produto: " + e.getMessage());
        }
        return false;
    }
    
    public LinkedList<Produto> verProdutosLista() {
        //copia
        return new LinkedList<>(listaProdutos);
    }
    
    public LinkedList<Produto> verProdutosSQL() throws SQLException { //devolve em forma de lista
        LinkedList<Produto> produtos = new LinkedList<>();
        String sql = "SELECT * FROM produto";

        try (Connection conector = ModuloConexao.conector(); 
             PreparedStatement executa = conector.prepareStatement(sql);
             ResultSet rs = executa.executeQuery()) {

            while (rs.next()) {
                Produto p = new Produto();
                p.setIdProduto(rs.getInt("id_produto"));
                p.setNome(rs.getString("nome"));
                p.setDescricao(rs.getString("descricao"));
                p.setMarca(rs.getString("marca"));
                p.setPreco(rs.getDouble("preco"));
                produtos.add(p);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar produtos: " + e.getMessage());
            throw e;
        }

        return produtos;
    }
}











