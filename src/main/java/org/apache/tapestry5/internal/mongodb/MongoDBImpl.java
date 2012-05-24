package org.apache.tapestry5.internal.mongodb;

import com.mongodb.DB;
import com.mongodb.Mongo;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.services.ThreadCleanupListener;
import org.apache.tapestry5.mongodb.MongoDB;
import org.apache.tapestry5.mongodb.MongoDBSource;
import org.apache.tapestry5.mongodb.MongoDBSymbols;
import org.slf4j.Logger;

/**
 *
 */
public class MongoDBImpl implements MongoDB, ThreadCleanupListener
{
    private final Logger logger;

    private final Mongo mongo;

    private final String defaultDbName;
    private final boolean consistentRequest;

    private DB db;

    public MongoDBImpl(Logger logger, MongoDBSource mongoDBSource,
           String defaultDbName, boolean consistentRequest)
    {
        this.logger = logger;
        this.mongo = mongoDBSource.getMongo();
        this.defaultDbName = defaultDbName;
        this.consistentRequest = consistentRequest;
    }

    @Override
    public DB getDefaultMongoDb()
    {
        db = getMongoDb(defaultDbName);

        return db;
    }

    @Override
    public DB getMongoDb(String dbname)
    {
        db = mongo.getDB(dbname);

        if (consistentRequest)
        {
            db.requestStart();
            db.requestEnsureConnection();
        }

        return db;
    }

    @Override
    public void threadDidCleanup()
    {
        if (consistentRequest)
            db.requestDone();
    }
}
