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

	private final boolean secureMode;
	private final String dbUsername;
	private final String dbPassword;

    private DB db;

    public MongoDBImpl(Logger logger, MongoDBSource mongoDBSource,
           String defaultDbName, boolean consistentRequest,
		   boolean secureMode, String dbUsername, String dbPassword)
    {
        this.logger = logger;
        this.mongo = mongoDBSource.getMongo();
        this.defaultDbName = defaultDbName;
        this.consistentRequest = consistentRequest;

		this.secureMode = secureMode;
		this.dbUsername = dbUsername;
		this.dbPassword = dbPassword;
    }

    @Override
    public DB getDefaultMongoDb()
    {
        return buildDbSession(defaultDbName);
    }

    @Override
    public DB getMongoDb(String dbname)
    {
		return buildDbSession(dbname);
    }

    @Override
    public void threadDidCleanup()
    {
        if (consistentRequest)
            db.requestDone();
    }


	private final DB buildDbSession(String dbname)
	{
		db = mongo.getDB(dbname);

		if (consistentRequest)
		{
			db.requestStart();
			db.requestEnsureConnection();
		}

		if (secureMode)
		{
			db.authenticate(dbUsername, dbPassword.toCharArray());
		}

		return db;
	}
}
