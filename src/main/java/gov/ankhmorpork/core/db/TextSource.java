package gov.ankhmorpork.core.db;

import gov.ankhmorpork.core.BookText;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Created by spooc on 1/3/2017.
 */
public class TextSource {
    public static String getWyrdSistersFirstFew() {
        BookText wyrdSisters = new BookText(getWyrdSisters());

        String lastChapter = Arrays.stream(wyrdSisters.getChapters()).skip(2).findFirst().get();
        int lastChapterEndIndex = wyrdSisters.getContents().lastIndexOf(lastChapter);

        String selection = wyrdSisters.getContents().substring(0,lastChapterEndIndex);
        return selection;
    }

    public static String getSillyHannibalText() {
        return "\"Hello Clarice,\" Hannibal said. \"I've been waiting a long time.\"\n"+
            "Clarice explained, \"Oh, this is just terrible. What ever shall I do?\"\n" +
            "\"You have no idea,\" Hannibal Lector replied with ease, \"I've been waiting a long time indeed.\"\n"+
            "\"We'll see about that,\" said Clarice";
    }

    public static String getWyrdSisters() {
        String pathname = "../data/wyrd_sisters.txt";
        Path path = Paths.get(pathname);
        String contents = "";
        try {
            contents = new String(Files.readAllBytes(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contents;
    }

    public static String getDeathTest() {
        String pathname = "../data/death_test.txt";
        Path path = Paths.get(pathname);
        String contents = "";
        try {
            contents = new String(Files.readAllBytes(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contents;
    }
}
