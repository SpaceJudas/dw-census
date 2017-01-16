package gov.ankhmorpork.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Book {
    private @Id @GeneratedValue Long id;
    private String name;
    @Column(name="text",
        columnDefinition="CLOB NOT NULL")
    @Lob
    private String text;
    private String paragraphSeparator;
    private String chapterSeparator;

    private Book() {}
    public Book(String name) {
        this.name = name;
        this.text = "";
    }
    public Book(String name, String text) {
        this.name = name;
        this.text = text;
    }
    public Book(String name, String text, String chapterSeparator, String paragraphSeparator) {
        this.name = name;
        this.text = text;
        this.paragraphSeparator = paragraphSeparator;
        this.chapterSeparator = chapterSeparator;
    }
}
