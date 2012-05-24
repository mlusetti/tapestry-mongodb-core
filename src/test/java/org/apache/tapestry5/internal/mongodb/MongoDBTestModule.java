package org.apache.tapestry5.internal.mongodb;

import com.mongodb.ServerAddress;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.mongodb.MongoDBSymbols;

import java.net.UnknownHostException;

/**
 *
 */
public class MongoDBTestModule
{

    public static void contributeApplicationDefaults(MappedConfiguration<String, String> configuration)
    {
        configuration.add(MongoDBSymbols.DEFAULT_DB_NAME, "TapestryMongoTest");
    }
}
