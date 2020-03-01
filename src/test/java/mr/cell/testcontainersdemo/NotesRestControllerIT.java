package mr.cell.testcontainersdemo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ContextConfiguration(initializers = { NotesRestControllerIT.Initializer.class })
public class NotesRestControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Container
    private final static GenericContainer mongoDbContainer = new GenericContainer<>("mongo:latest")
            .withExposedPorts(27017);

    @BeforeAll
    public static void beforeAll() {
        mongoDbContainer.start();
    }

    @Test
    public void shouldSaveNoteSuccessfully() throws Exception {
        final Note note = new Note();
        note.setAuthor("author");
        note.setTitle("title");
        note.setBody("body");

        final ResponseEntity<Note> response = restTemplate.postForEntity("http://localhost:" + port + "/notes",
                note, Note.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody().getAuthor()).isEqualTo("author");
        assertThat(response.getBody().getTitle()).isEqualTo("title");
        assertThat(response.getBody().getBody()).isEqualTo("body");
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
