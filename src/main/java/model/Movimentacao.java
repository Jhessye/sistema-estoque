package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *
 * @author Jhessye Lorrayne
 */
public abstract class Movimentacao {

    private int idMovimentacao;
    private Produto produto;
    private LocalDate data;

    public Movimentacao(int idMovimentacao, Produto produto, LocalDate data) {
        this.idMovimentacao = idMovimentacao;
        this.produto = produto;
        this.data = data;
    }

    public Movimentacao() {
    }

    public int getIdMovimentacao() {
        return idMovimentacao;
    }

    public void setIdMovimentacao(int idMovimentacao) {
        this.idMovimentacao = idMovimentacao;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
    
    public LocalDate getData() {
        return data;
    }

    public void setData(String data) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            this.data = LocalDate.parse(data, formatter);
        } catch (DateTimeParseException e) {
            System.err.println("Erro ao converter a data");
        }
    }

    public double valor(Produto produto){ //saida é negativo
        return this.produto.getQuantidade()*this.produto.getPreco();
    }
    
    public abstract boolean movimenta(Produto produto);

    @Override
    public String toString() {
        return "Movimentacao{" + "idMovimentacao=" + idMovimentacao + ", produto=" + produto + ", data=" + data + '}';
    }
    
}
