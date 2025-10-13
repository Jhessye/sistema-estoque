/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.SQLException;
import java.util.LinkedList;
import model.Movimentacao;
import persisted.MovimentacaoDAO;
/**
 *
 * @author Jhessye Lorrayne
 */
public class MovimentacaoController{

    public MovimentacaoController() {
    }
    //pensar 
    public boolean inserirMovimentacao(Movimentacao movimentacao) throws SQLException{
       
        MovimentacaoDAO daoM = new MovimentacaoDAO();
        
        return daoM.inserirMovimentacao(movimentacao);
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
            return daoM.verMovimentacoesSQL();
        } else {
            return daoM.verMovimentacoesLista();
        }
    }
    
    public static int totalRegistrosMovimentacoes() throws SQLException{
        
        MovimentacaoDAO daoM = new MovimentacaoDAO();
        
        return daoM.quantidadeRegistrosMovimentacao();
    }
    
}
