package mr.cell.testcontainersdemo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NotesRepository extends MongoRepository<Note, UUID> {
}
