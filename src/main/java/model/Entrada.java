/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Jhessye Lorrayne
 */
public class Entrada extends Movimentacao{

    public Entrada() {
        super();
    }
    
    @Override
    public boolean movimenta(Produto produto) {
        if(produto.getQuantidade()==0){
           produto.setQuantidadeSoma(1);
        }
        
        int novaQuantidade = produto.getQuantidade()+1;
        produto.setQuantidadeSoma(novaQuantidade);
        return true;
    }

}
