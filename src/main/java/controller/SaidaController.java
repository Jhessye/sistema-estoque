/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.SQLException;
import model.Movimentacao;
import persisted.SaidaDAO;
import model.Saida;
/**
 *
 * @author Jhessye Lorrayne
 */
public class SaidaController extends MovimentacaoController {

    public SaidaController() {
        super();
    }
    
    @Override
    public boolean inserirMovimentacao(Movimentacao movimentacao) throws SQLException {
        //valor negativo antes de inserir
        double valorPositivo = movimentacao.getValor();
        movimentacao.setValor(-Math.abs(valorPositivo)); //negativo
        
        SaidaDAO daoS = new SaidaDAO();
        Saida s = (Saida)movimentacao;
        return daoS.inserirSaida(s);
    }
    
}
