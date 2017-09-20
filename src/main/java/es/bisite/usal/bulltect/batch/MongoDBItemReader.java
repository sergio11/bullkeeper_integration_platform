package es.bisite.usal.bulltect.batch;


import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.ClassUtils;
import org.springframework.core.convert.converter.Converter;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;
import com.mongodb.util.JSONParseException;
import java.util.List;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
/**
 *
 * @author sergio
 */
public class MongoDBItemReader extends AbstractItemCountingItemStreamItemReader<Object>
        implements InitializingBean {
    
    private static final String NO_MORE = "no more";
    
    // MongoDB connection pool.
    protected Mongo mongo;

    // Name of the database to read from.
    protected String db;

    // Name of the collection to read from.
    protected String collection;
    
    // Query in JSON notation (If no query is given, the whole collection is read.)
    protected String query;

    // JSON document that filters the returned fields (optinal).
    protected String keys;
    
    // Number of documents to read in one batch (optional).
    protected int batchSize;
    
    // Sort criteria in JSON notation
    protected String sort;
    
    // Use a snapshot query (optional).
    protected boolean snapshot;
    
    // Limit the amount of read documents (optional).
    protected int limit;
    
    // Skip the first n document in the query result (optional).
    protected int skip;
    
    // Cursor pointing to the current document.
    protected DBCursor cursor;
    
    // Custom converter to map
    protected Converter<DBObject, ?> converter;

    public MongoDBItemReader() {
        setName(ClassUtils.getShortName(MongoDBItemReader.class));
    }

    @Override
    protected void jumpToItem(int itemIndex) throws Exception {
        if (itemIndex < 0) {
            throw new IllegalArgumentException("Index must not be negative: " + itemIndex);
        }
        cursor.skip(itemIndex + skip);
    }

    @Override
    protected void doOpen() throws Exception {
        // negative skip value are not supported
        if (skip < 0) {
            throw new IllegalArgumentException("Negative skip values are not supported: " + skip);
        }
        // do NOT read from a db that does not exist
        if (!dbExists()) {
            throw new IllegalArgumentException("No such database: " + db);
        }
        final DB mongoDB = mongo.getDB(db);
        // do NOT read from collections that do not exist
        if (!mongoDB.collectionExists(collection)) {
            throw new IllegalArgumentException("No such collection: " + collection);
        }
        // create the cursor
        cursor = createCursor(mongoDB.getCollection(collection));
    }

    @Override
    public Object doRead() throws Exception {
        try {
        	if(cursor.hasNext()) {
        		return converter != null ? converter.convert(cursor.next()) : cursor.next();
        	}
        	return null;
            
        } catch (RuntimeException e) {
            if (NO_MORE.equals(e.getMessage())) {
                return null;
            } else {
                throw e;
            }
        }
    }

    @Override
    protected void doClose() throws Exception {
        if (cursor != null) {
            cursor.close();
        }
    }

    private boolean dbExists() {
        List<String> dbNames = mongo.getDatabaseNames();
        return dbNames != null && dbNames.contains(db);
    }

    private DBCursor createCursor(DBCollection coll) {
        DBCursor crsr;
        DBObject ref = null;
        DBObject keysDoc = null;

        if (StringUtils.hasText(query)) {
            ref = parseDocument(query);
        }

        if (StringUtils.hasText(keys)) {
            keysDoc = parseDocument(keys);
        }
        crsr = coll.find(ref, keysDoc);
        if (StringUtils.hasText(sort)) {
            crsr = crsr.sort(parseDocument(sort));
        }
        if (batchSize != 0) {
            crsr = crsr.batchSize(batchSize);
        }
        if (snapshot) {
            crsr = crsr.snapshot();
        }
        if (limit != 0) {
            crsr = crsr.limit(limit);
        }
        if (skip > 0) {
            crsr = crsr.skip(skip);
        }
        return crsr;
    }

    private static DBObject parseDocument(String json) {
        try {
            return (DBObject) JSON.parse(json);
        } catch (JSONParseException e) {
            throw new IllegalArgumentException("Not a valid JSON document: " + json, e);
        }
    }

    public void setMongo(Mongo mongo) {
        this.mongo = mongo;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

    public void setConverter(Converter<DBObject, ?> converter) {
        this.converter = converter;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void setSnapshot(boolean snapshot) {
        this.snapshot = snapshot;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(mongo, "A Mongo instance is required");
        Assert.hasText(db, "A database name is required");
        Assert.hasText(collection, "A collection name is required");
    }
}
