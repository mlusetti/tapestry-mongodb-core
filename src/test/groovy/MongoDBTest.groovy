import spock.lang.Specification
import org.apache.tapestry5.ioc.annotations.SubModule
import org.apache.tapestry5.mongodb.MongodbCoreModule
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
import de.flapdoodle.embed.mongo.config.MongodConfig
import de.flapdoodle.embed.mongo.MongodExecutable
import de.flapdoodle.embed.mongo.MongodProcess
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.process.runtime.Network
import de.flapdoodle.embed.mongo.MongodStarter
import jmockmongo.MockMongo

@SubModule([ MongodbCoreModule.class, MongoDBTestModule.class ])
class MongoDBTest extends Specification
{
    final int total = 1000

    @Inject MongoDBSource mongoDBSource
    @Inject @Shared MongoDB mongoDB

    static int PORT = 12345
    static MongodExecutable mongodExe
    static MongodProcess mongod

    static MongoCollection peoples;
    static Jongo jongo

    def setupSpec()
    {
        MongodStarter runtime = MongodStarter.getDefaultInstance();
        mongodExe = runtime.prepare(new MongodConfig(Version.Main.V2_0, 12345, Network.localhostIsIPv6()));
        mongod = mongodExe.start();

        jongo = new Jongo(mongoDB.getDefaultMongoDb())
        peoples = jongo.getCollection("peoples")
    }

    def cleanupSpec()
    {
        peoples.drop()
        if (mongod != null) mongod.stop()
        if (mongodExe != null) mongodExe.cleanup()
    }

    def "Lets check mongodb source"()
    {
        expect:
        mongoDBSource.getMongo() != null
    }

    def "Lets populate it"()
    {
        when:
        for (int i = 0; i < total; i++)
        {
            People p = new People();
            p.setBirthDate(new Date());
            p.setName("Name-" + i);
            p.setSurname("Surname-" + i);
            peoples.save(p)
        }

        then:
        peoples.count() == total
        People p38 = peoples.findOne("{name: 'Name-38'}").as(People.class)
        p38.getSurname().equals("Surname-38")

        cleanup:
        peoples.remove("{}")
    }
}