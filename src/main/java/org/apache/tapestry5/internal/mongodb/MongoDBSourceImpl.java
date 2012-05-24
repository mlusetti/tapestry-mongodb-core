package org.apache.tapestry5.internal.mongodb;

import com.mongodb.*;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.services.SymbolSource;
import org.apache.tapestry5.ioc.services.ThreadCleanupListener;
import org.apache.tapestry5.mongodb.MongoDBSource;
import org.apache.tapestry5.mongodb.MongoDBSymbols;
import org.slf4j.Logger;

import java.util.List;

/**
 * Default implementation for {@link org.apache.tapestry5.mongodb.MongoDBSource}
 */
public class MongoDBSourceImpl implements MongoDBSource
{
    private final Logger logger;

    private final Mongo mongo;
    private final String defaultDbName;
    private final boolean consistentRequest;


    public MongoDBSourceImpl(Logger logger,
            @Symbol(MongoDBSymbols.DEFAULT_DB_NAME) String defaultDbName,
            @Symbol(MongoDBSymbols.CONNECTIONS_PER_HOSTS) int connectionPerHost,
            @Symbol(MongoDBSymbols.FSYNC) boolean fsync,
            @Symbol(MongoDBSymbols.J) boolean j,
            @Symbol(MongoDBSymbols.W) int w,
            @Symbol(MongoDBSymbols.WTIMEOUT) int wtimeout,
            @Symbol(MongoDBSymbols.READ_PREFERENCE) ReadPreference readPreference,
            @Symbol(MongoDBSymbols.WRITE_CONCERN) WriteConcern writeConcern,
            @Symbol(MongoDBSymbols.CONSISTENT_REQUEST) boolean consistentRequest,
            List<ServerAddress> serverAddresses)
    {
        this.logger = logger;
        this.defaultDbName = defaultDbName;
        this.consistentRequest = consistentRequest;

        MongoOptions options = new MongoOptions();
        options.connectionsPerHost = connectionPerHost;
        options.fsync = fsync;
        options.j = j;
        options.w = w;
        options.wtimeout = wtimeout;

        mongo = new Mongo(serverAddresses, options);

        mongo.setReadPreference(readPreference);
        mongo.setWriteConcern(writeConcern);
    }


    @Override
    public Mongo getMongo()
    {
        return this.mongo;
    }
}
