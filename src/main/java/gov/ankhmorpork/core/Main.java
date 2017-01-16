package gov.ankhmorpork.core;

import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import gov.ankhmorpork.core.corenlp.Annotators.DeathAnnotator;
import gov.ankhmorpork.core.corenlp.BookPipeline;
import gov.ankhmorpork.core.db.TextSource;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        String wyrdSisters = TextSource.getWyrdSisters();
        String deathTest = TextSource.getDeathTest();
        String deathNewTest = "\"OKAY, I get it,\", said Jim. \"You can stop vomiting everywhere. Thanks.\"";
        String deathNewNewTest = "OKAY, THAT'S FINE, said Death. JUST LEAVE ME THEN. LIKE I CARE.";
        DeathAnnotator da = new DeathAnnotator();
        String deathTestAfterPreprocess = da.preprocess(deathNewNewTest);

        Annotation splitText = new Annotation(deathNewNewTest);
        BookPipeline.Sentences().annotate(splitText);
        List<CoreMap> originalDeathQuotes = da.getProbableDeathQuotes(splitText);

        Annotation a = new Annotation(deathTestAfterPreprocess);
        BookPipeline.AllTheThings().annotate(a);

        String deathTestAfterPostProcess = da.postprocess(a, originalDeathQuotes);



        System.out.println(deathTestAfterPreprocess);
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
