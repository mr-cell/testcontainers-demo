package mr.cell.testcontainersdemo;


public abstract class AbstractIntegrationTest {

    private static final int DEFAULT_MONGODB_PORT = 27017;

    static {
        final MongoDbContainer mongo = new MongoDbContainer(DEFAULT_MONGODB_PORT);
        mongo.start();

        System.setProperty("spring.data.mongodb.host", mongo.getContainerIpAddress());
        System.setProperty("spring.data.mongodb.port", mongo.getMappedPort(DEFAULT_MONGODB_PORT).toString());
    }
}
