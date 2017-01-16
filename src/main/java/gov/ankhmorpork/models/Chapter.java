package gov.ankhmorpork.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Chapter {
    private @Id @GeneratedValue Long id;
    private Long bookId;
    private Integer chapterIndex;

    private Chapter() {}
    public Chapter(Long bookId, Integer chapterIndex) {
        this.bookId = bookId;
        this.chapterIndex = chapterIndex;
    }
}
