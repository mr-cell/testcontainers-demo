package mr.cell.testcontainersdemo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NotesRestController {

    private final NotesService notes;

    public NotesRestController(NotesService notes) {
        this.notes = notes;
    }

    @GetMapping
    public List<Note> getNotes() {
        return notes.getNotes();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Note saveNote(@RequestBody final Note note) {
        return notes.saveNote(note);
    }
}
