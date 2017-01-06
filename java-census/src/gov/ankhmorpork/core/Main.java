package gov.ankhmorpork.core;

import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import gov.ankhmorpork.core.corenlp.Annotators.DeathAnnotator;
import gov.ankhmorpork.core.corenlp.BookPipeline;
import gov.ankhmorpork.core.db.TextSource;
import gov.ankhmorpork.test.ProtobufTest;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String wyrdSisters = TextSource.getWyrdSisters();
        BookText bookText = new BookText(wyrdSisters);
        DeathAnnotator.addQuotationMarks(wyrdSisters);
    }

    public static void processAndCacheBookChapters(BookText book) {
        StanfordCoreNLP annotator = BookPipeline.AllTheThings();

        String[] chapters = book.getChapters();
        int chapterCount = chapters.length;
        for (int i = 0; i<chapterCount; i++) {
            Annotation ca = new Annotation(chapters[i]);
            annotator.annotate(ca);

            AnnotationCache.save("ws-ch-"+i, ca);
        }
    }

    public static void processAndCacheWholeBook(BookText book) {
        StanfordCoreNLP annotator = BookPipeline.AllTheThings();

        String contents = book.getContents();

        Annotation ca = new Annotation(contents);
        annotator.annotate(ca);

        AnnotationCache.save("ws-fullbook", ca);
    }
}
