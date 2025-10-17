/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.SQLException;
import java.util.LinkedList;
import model.Entrada;
import model.Movimentacao;
import model.Saida;
import persisted.MovimentacaoDAO;
/**
 *
 * @author Jhessye Lorrayne
 */
public class MovimentacaoController{

    public MovimentacaoController() {
    }
    //pensar 
    public static boolean inserirEntrada(Entrada entrada) throws SQLException{
       
        MovimentacaoDAO daoM = new MovimentacaoDAO();
        
        return daoM.inserirEntrada(entrada);
    }
    
    public static boolean inserirSaida(Saida saida) throws SQLException{
       
        MovimentacaoDAO daoM = new MovimentacaoDAO();
        
        return daoM.inserirSaida(saida);
    }
   
    public static boolean alterarMovimentacao(Movimentacao movimentacao, String atributo) throws SQLException{
        
        MovimentacaoDAO daoM = new MovimentacaoDAO();
        
        return daoM.atualizarMovimentacao(movimentacao, atributo);
    }
    
    public static boolean excluirMovimentacao(Movimentacao movimentacao) throws SQLException{
        
        MovimentacaoDAO daoM = new MovimentacaoDAO();
        
        return daoM.removerMovimentacao(movimentacao);
    }
    
    public static LinkedList<Movimentacao> mostrarMovimentacoes(String escolha) throws SQLException{
        
        MovimentacaoDAO daoM = new MovimentacaoDAO();
        
        if (escolha.equals("Banco de Dados")){
            return daoM.verMovimentacaoSQL();
        } else {
            return daoM.verMovimentacaoLista();
        }
    }
    
    public static int totalRegistrosMovimentacoes() throws SQLException{
        
        MovimentacaoDAO daoM = new MovimentacaoDAO();
        
        return daoM.quantidadeRegistrosMovimentacao();
    }
    
}
