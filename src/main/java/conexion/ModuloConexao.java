package conexion;

import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.ReturnDocument;
import org.bson.Document;

public class ModuloConexao {

    private static final String CONNECTION_STRING = "mongodb://localhost:27017"; // ajustar se necessário
    private static final String DATABASE_NAME = "estocar";
    private static MongoClient mongoClient = null;

    public static synchronized MongoClient getClient() {
        if (mongoClient == null) {
            mongoClient = MongoClients.create(CONNECTION_STRING);
        }
        return mongoClient;
    }

    public static MongoDatabase getDatabase() {
        return getClient().getDatabase(DATABASE_NAME);
    }

    public static MongoCollection<Document> getCollection(String name) {
        return getDatabase().getCollection(name);
    }

    /**
     * Retorna um id inteiro único para a sequência nomeada.
     * Mantém uma coleção `counters` com documentos { _id: <nome>, seq: <int> }.
     */
    public static int getNextSequence(String name) {
        MongoCollection<Document> counters = getCollection("counters");
        Document filter = new Document("_id", name);
        Document update = new Document("$inc", new Document("seq", 1));
        Document updated = counters.findOneAndUpdate(filter, update,
                new FindOneAndUpdateOptions().upsert(true).returnDocument(ReturnDocument.AFTER));

        if (updated == null) {
            // Em caso improvável, criar o documento inicial
            counters.insertOne(new Document("_id", name).append("seq", 1));
            return 1;
        }

        return updated.getInteger("seq", 1);
    }
}
