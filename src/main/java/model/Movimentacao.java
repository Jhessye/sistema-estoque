/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Jhessye Lorrayne
 */
public class Movimentacao {
    
    private int idMovimentacao, quantidade, idProduto;
    private String tipo, data;
    private double valor;

    public Movimentacao(int quantidade, int idProduto, String tipo, String data, double valor) {
        this.quantidade = quantidade;
        this.idProduto = idProduto;
        this.tipo = tipo;
        this.data = data;
        this.valor = valor;
    }

    public Movimentacao() {
    }

    public int getIdProduto() {
        return idProduto;
    }

    //FK
    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public int getIdMovimentacao() {
        return idMovimentacao;
    }

    public void setIdMovimentacao(int idMovimentacao) {
        this.idMovimentacao = idMovimentacao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Movimentacao{" + "idMovimentacao=" + idMovimentacao + ", quantidade=" + quantidade + ", tipo=" + tipo + ", data=" + data + ", valor=" + valor + '}';
    }
    
}
