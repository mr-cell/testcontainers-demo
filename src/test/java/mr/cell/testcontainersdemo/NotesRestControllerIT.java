package mr.cell.testcontainersdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NotesRestControllerIT extends AbstractIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldSaveNoteSuccessfully() {
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
}
