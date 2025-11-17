/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Jhessye Lorrayne
 */
public class Produto {
    
    private int idProduto, quantidade;
    private Categoria categoria;
    private String nome, descricao, marca;
    private double preco;

    public Produto(int idProduto, int quantidade, Categoria categoria, String nome, String descricao, String marca, double preco) {
        this.idProduto = idProduto;
        this.quantidade = quantidade;
        this.categoria = categoria;
        this.nome = nome;
        this.descricao = descricao;
        this.marca = marca;
        this.preco = preco;
    }

    public Produto() {
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidadeSoma(int quantidade) {
        this.quantidade += quantidade;
    }
    
    //DAO
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
    public void setQuantidadeSubtrai(int quantidade) {
        this.quantidade -= quantidade;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return "Produto{" + "idProduto=" + idProduto + ", quantidade=" + quantidade + ", categoria=" + categoria + ", nome=" + nome + ", descricao=" + descricao + ", marca=" + marca + ", preco=" + preco + '}';
    }
    
}
