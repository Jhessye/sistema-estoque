/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.SQLException;
import java.util.LinkedList;
import model.Categoria;
import persisted.CategoriaDAO;
/**
 *
 * @author Jhessye Lorrayne
 */
public class CategoriaController{
    
    public static boolean inserirCategoria(Categoria categoria) throws SQLException{

        CategoriaDAO daoC = new CategoriaDAO();

        return daoC.inserirCategoria(categoria);
    }

    public static boolean alterarCategoria(Categoria categoria, String atributo) throws SQLException{

        CategoriaDAO daoC = new CategoriaDAO();

        return daoC.atualizarCategoria(categoria, atributo);
    }

    public static boolean excluirCategoria(Categoria categoria) throws SQLException{

        CategoriaDAO daoC = new CategoriaDAO();

        return daoC.removerCategoria(categoria);
    }

    public static LinkedList<Categoria> mostrarCategorias(String escolha) throws SQLException{

        CategoriaDAO daoC = new CategoriaDAO();

        if (escolha.equals("Banco de Dados")){
            return daoC.verCategoriasSQL();
        } else {
            return daoC.verCategoriasLista();
        }
    }
    
    public static int totalRegistrosCategoria() throws SQLException{
        
        CategoriaDAO daoC = new CategoriaDAO();
        
        return daoC.quantidadeRegistrosCategoria();
    }
    
}
