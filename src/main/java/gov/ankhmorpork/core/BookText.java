package gov.ankhmorpork.core;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import gov.ankhmorpork.core.corenlp.BookPipeline;

import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class BookText {
    private String contents;

    //Expected 2 breaks between paragraphs (based on wyrd sisters)
    private Pattern paragraphSeparator = Pattern.compile("\n+");
    //Expected 3 breaks between paragraphs (based on wyrd sisters)
    private Pattern chapterSeparator = Pattern.compile("\n{3,}");

    private static StanfordCoreNLP nlpPipe = BookPipeline.Sentences();

    public BookText(String contents) {
        this.contents = contents;
    }

    public Annotation getAnnotation() {
        Annotation a = new Annotation(this.contents);
        nlpPipe.annotate(a);
        return a;
    }

    public String getContents() {
        return this.contents;
    }

    public String[] getSentences() {
        Annotation a = this.getAnnotation();
        nlpPipe.annotate(a);
        List<CoreMap> sentenceAnnotations = a.get(CoreAnnotations.SentencesAnnotation.class);
        String[] sentences = (String[]) sentenceAnnotations.stream().map(s->s.get(CoreAnnotations.TextAnnotation.class)).toArray();
        return sentences;
    }

    public String[] getParagraphs() {
        return paragraphSeparator.split(this.contents);
    }

    public String[] getChapters() {
        return chapterSeparator.split(this.contents);
    }
}
