package persisted;

import conexion.ModuloConexao;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.AggregateIterable;

public class RelatorioDAO {

    public List<Object[]> relatorioMovimentacaoPorProduto() {
        MongoCollection<Document> movCol = ModuloConexao.getCollection("movimentacoes");
        List<Document> pipeline = new ArrayList<>();
        pipeline.add(new Document("$lookup", new Document("from", "produtos").append("localField", "id_produto").append("foreignField", "id_produto").append("as", "produto")));
        pipeline.add(new Document("$unwind", "$produto"));
        pipeline.add(new Document("$group", new Document("_id", "$produto.nome").append("total", new Document("$sum", "$valor"))));
        pipeline.add(new Document("$project", new Document("produto", "$_id").append("total_movimentado", "$total")));
        pipeline.add(new Document("$sort", new Document("produto", 1)));

        AggregateIterable<Document> agg = movCol.aggregate(pipeline);
        List<Object[]> out = new ArrayList<>();
        for (Document d : agg) {
            String produto = d.getString("produto");
            Number total = (Number) d.get("total_movimentado");
            out.add(new Object[] { produto, total == null ? 0 : total.doubleValue() });
        }
        return out;
    }

    public List<Object[]> relatorioMovimentacoesDetalhado() {
        MongoCollection<Document> movCol = ModuloConexao.getCollection("movimentacoes");
        List<Document> pipeline = new ArrayList<>();
        pipeline.add(new Document("$lookup", new Document("from", "produtos").append("localField", "id_produto").append("foreignField", "id_produto").append("as", "produto")));
        pipeline.add(new Document("$unwind", "$produto"));
        pipeline.add(new Document("$lookup", new Document("from", "categorias").append("localField", "produto.id_categoria").append("foreignField", "id_categoria").append("as", "categoria")));
        pipeline.add(new Document("$unwind", new Document("path", "$categoria").append("preserveNullAndEmptyArrays", true)));
        pipeline.add(new Document("$project", new Document()
                .append("id_mov", "$id_movimentacoes")
                .append("data_mov", "$data")
                .append("produto", "$produto.nome")
                .append("quantidade", "$produto.quantidade")
                .append("valor", "$valor")
                .append("categoria", "$categoria.nome")
        ));
        pipeline.add(new Document("$sort", new Document("data_mov", -1).append("id_mov", -1)));

        AggregateIterable<Document> agg = movCol.aggregate(pipeline);
        List<Object[]> out = new ArrayList<>();
        for (Document d : agg) {
            out.add(new Object[] {
                d.getInteger("id_mov"),
                d.getString("data_mov"),
                d.getString("produto"),
                d.getInteger("quantidade", 0),
                d.get("valor"),
                d.getString("categoria")
            });
        }
        return out;
    }
}
