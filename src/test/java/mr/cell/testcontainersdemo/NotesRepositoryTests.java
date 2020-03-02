package mr.cell.testcontainersdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class NotesRepositoryTests extends AbstractIntegrationTest {

    @Autowired
    private NotesRepository systemUnderTest;

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
}
