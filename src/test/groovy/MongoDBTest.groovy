import spock.lang.Specification
import org.apache.tapestry5.ioc.annotations.SubModule
import org.apache.tapestry5.mongodb.MongoDBModule
import org.apache.tapestry5.ioc.annotations.Inject
import org.apache.tapestry5.mongodb.MongoDBSource
import org.apache.tapestry5.internal.mongodb.MongoDBTestModule

@SubModule( MongoDBModule.class)
class MongoDBTest extends Specification
{
    @Inject
    MongoDBSource mongoDBSource;

    def "Lets check mongodb source"()
    {
        expect:
        mongoDBSource.getMongo() != null
    }
}