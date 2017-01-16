package gov.ankhmorpork;

import gov.ankhmorpork.models.Book;
import gov.ankhmorpork.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private final BookRepository repository;

    @Autowired
    public DatabaseLoader(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... strings) throws Exception {
        String bookName = "Wyrd Sisters";
        String bookText = new String(Files.readAllBytes(Paths.get("data/books/" + bookName + ".txt")));

        this.repository.save(new Book("Test", "The quick brown fox jumped over the lazy dog. Shortly thereafter, he died of overstimulation."));
        this.repository.save(new Book("Wyrd Sisters", bookText, "(\r?\n){2,}", "(\r?\n)+"));

    }
}
