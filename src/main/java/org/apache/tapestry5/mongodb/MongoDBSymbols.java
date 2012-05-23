package org.apache.tapestry5.mongodb;

/**
 * Configuration symbols, for use with contributions to
 * {@link org.apache.tapestry5.ioc.services.ApplicationDefaults}.
 * All {@link org.apache.tapestry5.ioc.annotations.Symbol} are relative to the usage of
 * <a href="http://www.mongodb.org">MongoDB</a> support within Apache Tapestry.
 *
 * @since 5.4
 */
public class MongoDBSymbols
{
    /**
     * The {@link com.mongodb.WriteConcern} to use. Default to {@link com.mongodb.WriteConcern.NORMAL}.
     */
    public static final String WRITE_CONCERN = "tapestry.mongodb.write_concern";

    /**
     * The {@link com.mongodb.ReadPreference} to use. Default to {@link com.mongodb.ReadPreference.PRIMARY}.
     */
    public static final String READ_PREFERENCE = "tapestry.mongodb.read_preference";

    // ########################################################################

    /**
     * The maximum number of connections allowed per host for this Mongo instance. Defaults to 10.
     */
    public static final String CONNECTIONS_PER_HOSTS = "tapestry.mongodb.conns-per-host";

    /**
     *  The "fsync" value of the global WriteConcern. Default is false.
     */
    public static final String FSYNC = "tapestry.mongodb.fsync";

    /**
     *  The "j" value of the global WriteConcern. Default to false
     */
    public static final String J = "tapestry.mongodb.j";

    /**
     * The "wtimeout" value of the global WriteConcern. Default is 0.
     */
    public static final String WTIMEOUT = "tapestry.mongodb.wtimeout";

}
