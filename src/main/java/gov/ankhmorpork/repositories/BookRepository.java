package gov.ankhmorpork.repositories;

import gov.ankhmorpork.models.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
}
