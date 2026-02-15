package persisted;

import conexion.ModuloConexao;
import java.sql.SQLException;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import model.Movimentacao;
import model.Produto;
import model.Entrada;
import model.Saida;
import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

public class MovimentacaoDAO {

    private LinkedList<Movimentacao> listaMovimentacao = new LinkedList<>();
    private ProdutoDAO produtoDAO = new ProdutoDAO(); // precisamos dele para buscar o produto

    public MovimentacaoDAO() throws SQLException {
        try {
            carregarListaMovimentacao();
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    private void carregarListaMovimentacao() {
        listaMovimentacao.clear();
        MongoCollection<Document> col = ModuloConexao.getCollection("movimentacoes");
        for (Document d : col.find()) {
            Movimentacao m = new Movimentacao() {
                @Override
                public boolean movimenta(Produto produto) {
                    return true;
                }
            };

            int idProduto = d.getInteger("id_produto", 0);
            Produto p = null;
            try {
                p = produtoDAO.buscarPorId(idProduto);
            } catch (SQLException ex) {
                // ignora, p pode ser nulo
            }

            m.setIdMovimentacao(d.getInteger("id_movimentacoes", 0));
            m.setProduto(p);
            Object dataObj = d.get("data");
            String dataStr = "";
            if (dataObj instanceof java.util.Date) {
                dataStr = new java.text.SimpleDateFormat("yyyy-MM-dd").format((java.util.Date) dataObj);
            } else if (dataObj != null) {
                dataStr = dataObj.toString();
            }
            m.setData(dataStr);

            listaMovimentacao.add(m);
        }
    }
    
    public boolean inserirEntrada(Entrada entrada) {
        try {
            MongoCollection<Document> col = ModuloConexao.getCollection("movimentacoes");
            double valorCalculado = entrada.valor(entrada.getProduto());

            int nextId = ModuloConexao.getNextSequence("movimentacoes");
            Document d = new Document("id_movimentacoes", nextId)
                    .append("data", entrada.getData())
                    .append("valor", valorCalculado)
                    .append("id_produto", entrada.getProduto().getIdProduto());

            col.insertOne(d);
            entrada.setIdMovimentacao(nextId);
            listaMovimentacao.add(entrada);

            // Atualiza quantidade do produto
            MongoCollection<Document> prodCol = ModuloConexao.getCollection("produtos");
            prodCol.updateOne(Filters.eq("id_produto", entrada.getProduto().getIdProduto()), Updates.set("quantidade", entrada.getProduto().getQuantidade()));

            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir entrada: " + e.getMessage());
            return false;
        }
    }

    public boolean inserirSaida(Saida saida) {
        try {
            MongoCollection<Document> col = ModuloConexao.getCollection("movimentacoes");
            double valorCalculado = saida.valor(saida.getProduto());

            int nextId = ModuloConexao.getNextSequence("movimentacoes");
            Document d = new Document("id_movimentacoes", nextId)
                    .append("data", saida.getData())
                    .append("valor", valorCalculado)
                    .append("id_produto", saida.getProduto().getIdProduto());

            col.insertOne(d);
            saida.setIdMovimentacao(nextId);
            listaMovimentacao.add(saida);

            // Atualiza quantidade do produto
            MongoCollection<Document> prodCol = ModuloConexao.getCollection("produtos");
            prodCol.updateOne(Filters.eq("id_produto", saida.getProduto().getIdProduto()), Updates.set("quantidade", saida.getProduto().getQuantidade()));

            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir saida: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizarMovimentacao(Movimentacao movimentacao, String atributo) {
        try {
            MongoCollection<Document> col = ModuloConexao.getCollection("movimentacoes");
            switch (atributo) {
                case "data" -> col.updateOne(Filters.eq("id_movimentacoes", movimentacao.getIdMovimentacao()), Updates.set("data", movimentacao.getData()));
                case "id_produto" -> col.updateOne(Filters.eq("id_movimentacoes", movimentacao.getIdMovimentacao()), Updates.set("id_produto", movimentacao.getProduto().getIdProduto()));
                case "quantidade" -> {
                    MongoCollection<Document> prodCol = ModuloConexao.getCollection("produtos");
                    prodCol.updateOne(Filters.eq("id_produto", movimentacao.getProduto().getIdProduto()), Updates.set("quantidade", movimentacao.getProduto().getQuantidade()));
                }
                default -> {
                    JOptionPane.showMessageDialog(null, "Atributo inválido!");
                    return false;
                }
            }
            atualizarNaLista(movimentacao);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar movimentação: " + e.getMessage());
            return false;
        }
    }

    private void atualizarNaLista(Movimentacao movimentacao) {
        for (int i = 0; i < listaMovimentacao.size(); i++) {
            if (listaMovimentacao.get(i).getIdMovimentacao() == movimentacao.getIdMovimentacao()) {
                listaMovimentacao.set(i, movimentacao);
                break;
            }
        }
    }
   
    public boolean removerMovimentacao(Movimentacao movimentacao) {
        try {
            MongoCollection<Document> col = ModuloConexao.getCollection("movimentacoes");
            col.deleteOne(Filters.eq("id_movimentacoes", movimentacao.getIdMovimentacao()));
            listaMovimentacao.remove(movimentacao);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao remover movimentação: " + e.getMessage());
            return false;
        }
    }

  
    public LinkedList<Movimentacao> verMovimentacaoSQL() throws SQLException {
        try {
            LinkedList<Movimentacao> movimentacoes = new LinkedList<>();
            MongoCollection<Document> col = ModuloConexao.getCollection("movimentacoes");
            for (Document d : col.find()) {
                Movimentacao m = new Movimentacao() {
                    @Override
                    public boolean movimenta(Produto produto) {
                        return true;
                    }
                };

                int idProduto = d.getInteger("id_produto", 0);
                Produto p = produtoDAO.buscarPorId(idProduto);

                m.setIdMovimentacao(d.getInteger("id_movimentacoes", 0));
                m.setProduto(p);
                Object dataObj = d.get("data");
                String dataStr = "";
                if (dataObj instanceof java.util.Date) {
                    dataStr = new java.text.SimpleDateFormat("yyyy-MM-dd").format((java.util.Date) dataObj);
                } else if (dataObj != null) {
                    dataStr = dataObj.toString();
                }
                m.setData(dataStr);

                movimentacoes.add(m);
            }
            return movimentacoes;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar movimentações: " + e.getMessage());
            throw new SQLException(e);
        }
    }

    public LinkedList<Movimentacao> verMovimentacaoLista() {
        return new LinkedList<>(listaMovimentacao);//copia da lista
    }

    //tela inical
    public int quantidadeRegistrosMovimentacao() throws SQLException {
        try {
            MongoCollection<Document> col = ModuloConexao.getCollection("movimentacoes");
            return (int) col.countDocuments();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao pegar registros de movimentações: " + e.getMessage());
            throw new SQLException(e);
        }
    }
}
