package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *
 * @author Jhessye Lorrayne
 */
public class Movimentacao {

    private int idMovimentacao, quantidade, idProduto;
    private String tipo;
    private LocalDate data;
    private double valor;
    
    public Movimentacao(int quantidade, int idProduto, String tipo, String data, double valor) {
        this.quantidade = quantidade;
        this.idProduto = idProduto;
        this.tipo = tipo;
        this.valor = valor;

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            this.data = LocalDate.parse(data, formatter);
        } catch (DateTimeParseException e) {
            System.err.println("Erro ao converter a data");
        }
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

    public LocalDate getData() {
        if (data == null){
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return data.format(formatter);
    }

    public void setData(String data) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            this.data = LocalDate.parse(data, formatter);
        } catch (DateTimeParseException e) {
            System.err.println("Erro ao converter a data");
        }
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() { 
        return "Movimentacao{" + "idMovimentacao=" + idMovimentacao + ", quantidade=" + quantidade +
        ", tipo=" + tipo + ", data=" + data + ", valor=" + valor + '}'; 
    }
}
