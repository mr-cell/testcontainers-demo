package mr.cell.testcontainersdemo;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataMongoTest
@Testcontainers
@ContextConfiguration(initializers = { NotesRepositoryTests.Initializer.class })
public class NotesRepositoryTests {

    @Autowired
    private NotesRepository systemUnderTest;

    @Container
    private final static GenericContainer mongoDbContainer = new GenericContainer<>("mongo:latest")
            .withExposedPorts(27017);

    @BeforeAll
    public static void beforeAll() {
        mongoDbContainer.start();
    }

    @Test
    public void shouldSaveNoteSuccessfully() {
        final Note note = new Note(UUID.randomUUID(), "author", "title", "body");

        final Note actual = systemUnderTest.save(note);

        assertThat(actual).isEqualTo(note);
    }

    @Test
    public void shouldGetNoteSuccessfully() {
        final Note note = new Note(UUID.randomUUID(), "author", "title", "body");
        systemUnderTest.save(note);

        final Note actual = systemUnderTest.findById(note.getId()).orElseThrow(IllegalStateException::new);

        assertThat(actual).isEqualTo(note);
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(final ConfigurableApplicationContext applicationContext) {
            TestPropertyValues values = TestPropertyValues.of(
                    "spring.data.mongodb.host=" + mongoDbContainer.getContainerIpAddress(),
                    "spring.data.mongodb.port=" + mongoDbContainer.getMappedPort(27017)

            );
            values.applyTo(applicationContext);
        }
    }
}
