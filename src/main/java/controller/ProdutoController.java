/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import java.sql.SQLException;
import java.util.LinkedList;
import model.Produto;
import persisted.ProdutoDAO;
/**
 *
 * @author Jhessye Lorrayne
 */
public class ProdutoController{
    
    public static boolean inserirProduto(Produto produto) throws SQLException{
        
        ProdutoDAO daoP = new ProdutoDAO();
        
        return daoP.inserirProduto(produto);
    }
    
    public static boolean alterarProduto(Produto produto, String atributo) throws SQLException{
        
        ProdutoDAO daoP = new ProdutoDAO();
        
        return daoP.atualizarProduto(produto, atributo);
    }
    
    public static boolean excluirProduto(Produto produto) throws SQLException{
        
        ProdutoDAO daoP = new ProdutoDAO();
        
        return daoP.removerProduto(produto);
    }
    
    public LinkedList<Produto> mostrarPordutos(String escolha) throws SQLException{
        
        ProdutoDAO daoP = new ProdutoDAO();
        
        if (escolha.equals("Banco de Dados")){
            return daoP.verProdutosSQL();
        } else {
            return daoP.verProdutosLista();
        }
    }
    
}
