package org.apache.tapestry5.mongodb;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.ServerAddress;
import org.apache.tapestry5.ioc.annotations.UsesOrderedConfiguration;

/**
 *
 */
@UsesOrderedConfiguration(ServerAddress.class)
public interface MongoDBSource
{
    /**
     * @return the {@link Mongo} database connection object.
     */
    public Mongo getMongo();
}
