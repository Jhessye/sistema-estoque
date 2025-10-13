/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.SQLException;
import model.Movimentacao;
import persisted.EntradaDAO;
import model.Entrada;
/**
 *
 * @author Jhessye Lorrayne
 */
public class EntradaController extends MovimentacaoController {

    public EntradaController() {
        super();
    }
    
    @Override
    public boolean inserirMovimentacao(Movimentacao movimentacao) throws SQLException {
        //valor positivo pra entrada
        double valor = movimentacao.getValor();
        movimentacao.setValor(Math.abs(valor)); //positivo
        
        EntradaDAO daoE = new EntradaDAO();
        Entrada e = (Entrada)movimentacao; //casting
        return daoE.inserirEntrada(e);
    }
    
}
