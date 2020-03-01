package mr.cell.testcontainersdemo;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NotesService {

    private final NotesRepository notes;

    public NotesService(NotesRepository notes) {
        this.notes = notes;
    }

    public List<Note> getNotes() {
        return notes.findAll();
    }

    public Note saveNote(final Note note) {
        if (note.getId() == null) {
            note.setId(UUID.randomUUID());
        }
        return notes.save(note);
    }
}
