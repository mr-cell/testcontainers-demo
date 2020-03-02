package mr.cell.testcontainersdemo;

import org.testcontainers.containers.GenericContainer;

public class MongoDbContainer extends GenericContainer {

    private static final String MONGO_DOCKER_IMAGE = "mongo";
    private static final String MONGO_DOCKER_IMAGE_VERSION = "latest";

    public MongoDbContainer(final int port) {
        super(MONGO_DOCKER_IMAGE + ":" + MONGO_DOCKER_IMAGE_VERSION);
        withExposedPorts(port);
    }
}
