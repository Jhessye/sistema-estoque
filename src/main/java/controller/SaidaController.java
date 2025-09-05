/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.SQLException;
import model.Movimentacao;
import persisted.MovimentacaoDAO;
/**
 *
 * @author Jhessye Lorrayne
 */
public class SaidaController extends MovimentacaoController {
    
    @Override
    public boolean inserirMovimentacao(Movimentacao movimentacao) throws SQLException {
        //valor negativo antes de inserir
        double valorPositivo = movimentacao.getValor();
        movimentacao.setValor(-Math.abs(valorPositivo)); //negativo
        
        MovimentacaoDAO daoM = new MovimentacaoDAO();
        return daoM.inserirMovimentacao(movimentacao);
    }
    
}
