/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Jhessye Lorrayne
 */
public class Saida extends Movimentacao {

    public Saida() {
        super();
    }
    
    @Override
    public boolean movimenta(Produto produto) {
        if(produto.getQuantidade()>0){
            int novaQuantidade = produto.getQuantidade()-1;
            produto.setQuantidade(novaQuantidade);
   
        }else{
            return false;
        }
        
        return true;
    }
    
}
