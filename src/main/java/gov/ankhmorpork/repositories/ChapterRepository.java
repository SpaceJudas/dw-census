package gov.ankhmorpork.repositories;

import gov.ankhmorpork.models.Book;
import org.springframework.data.repository.CrudRepository;

public interface ChapterRepository extends CrudRepository<Book, Long> {
}
