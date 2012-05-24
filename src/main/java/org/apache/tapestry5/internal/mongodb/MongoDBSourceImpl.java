package org.apache.tapestry5.internal.mongodb;

import com.mongodb.*;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.services.SymbolSource;
import org.apache.tapestry5.ioc.services.ThreadCleanupListener;
import org.apache.tapestry5.mongodb.MongoDBSource;
import org.apache.tapestry5.mongodb.MongoDBSymbols;
import org.slf4j.Logger;

import java.net.UnknownHostException;
import java.util.List;

/**
 * Default implementation for {@link org.apache.tapestry5.mongodb.MongoDBSource}
 */
public class MongoDBSourceImpl implements MongoDBSource
{
    private final Logger logger;

    private final Mongo mongo;


    public MongoDBSourceImpl(Logger logger,
            @Symbol(MongoDBSymbols.CONNECTIONS_PER_HOSTS) int connectionPerHost,
            @Symbol(MongoDBSymbols.FSYNC) boolean fsync,
            @Symbol(MongoDBSymbols.J) boolean j,
            @Symbol(MongoDBSymbols.W) int w,
            @Symbol(MongoDBSymbols.WTIMEOUT) int wtimeout,
            @Symbol(MongoDBSymbols.READ_PREFERENCE) ReadPreference readPreference,
            @Symbol(MongoDBSymbols.WRITE_CONCERN) WriteConcern writeConcern,
            List<ServerAddress> serverAddresses)
    {
        this.logger = logger;

        MongoOptions options = new MongoOptions();
        options.connectionsPerHost = connectionPerHost;
        options.fsync = fsync;
        options.j = j;
        options.w = w;
        options.wtimeout = wtimeout;

        if (serverAddresses.isEmpty())
            try {
                mongo = new Mongo();
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
        else
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
