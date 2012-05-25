import spock.lang.Specification
import org.apache.tapestry5.ioc.annotations.SubModule
import org.apache.tapestry5.mongodb.MongoDBModule
import org.apache.tapestry5.ioc.annotations.Inject
import org.apache.tapestry5.mongodb.MongoDBSource
import org.apache.tapestry5.internal.mongodb.MongoDBTestModule
import com.mongodb.Mongo
import org.jongo.Jongo
import org.jongo.MongoCollection
import org.apache.tapestry5.internal.mongodb.People
import org.apache.tapestry5.mongodb.MongoDB
import com.mongodb.DBCollection
import spock.lang.Shared

@SubModule([ MongoDBModule.class, MongoDBTestModule.class ])
class MongoDBTest extends Specification
{
    final int total = 10000;
    int count;

    @Inject
    MongoDBSource mongoDBSource;
    @Inject
    MongoDB mongoDB;

    MongoCollection peoples;


    def "Lets check mongodb source"()
    {
        expect:
        mongoDBSource.getMongo() != null
    }

    def setup()
    {
        Jongo jongo = new Jongo(mongoDB.getDefaultMongoDb());
        peoples = jongo.getCollection("peoples");
        for (int i = 0; i < total; i++)
        {
            People p = new People();
            p.setBirthDate(new Date());
            p.setName("Name-" + i);
            p.setSurname("Surname-" + i);
        }
    }

    def cleanup()
    {
        peoples.drop()
    }


    def "Lets check populate"()
    {
        when:
        DBCollection collection = mongoDB.defaultMongoDb.getCollection("peoples")
        count = collection.count()
        System.out.println("Count = " + count + " total = " + total)

        then:
        // count == total
        System.out.println("Count = " + count + " total = " + total)

    }
}