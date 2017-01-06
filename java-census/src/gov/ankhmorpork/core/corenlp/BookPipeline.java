package gov.ankhmorpork.core.corenlp;

import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.EntityMentionsAnnotator;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import gov.ankhmorpork.core.MyAnnotator;

import java.util.Properties;

/**
 * Created by spooc on 1/2/2017.
 *
 * See http://stanfordnlp.github.io/CoreNLP/annotators.html
 */
public class BookPipeline {
    private BookPipeline() { }

    public static StanfordCoreNLP Sentences() {
        Properties props = new Properties();
        props.put("annotators", "tokenize,ssplit");
        return new StanfordCoreNLP(props);
    }

    public static StanfordCoreNLP Default() {
        Properties props = new Properties();
        props.put("annotators", "tokenize,ssplit,pos,lemma,ner,parse,entitymentions,dcoref,sentiment,quote");
        return new StanfordCoreNLP(props);
    }

    public static StanfordCoreNLP NoQuotations() {
        Properties props = new Properties();
        props.put("annotators", "tokenize,ssplit,pos,lemma,ner,parse,dcoref,sentiment");
        return new StanfordCoreNLP(props);
    }

    public static StanfordCoreNLP NoMentions() {
        Properties props = new Properties();
        props.put("annotators", "tokenize,ssplit,pos,lemma,ner,parse,dcoref,sentiment,quote");
        return new StanfordCoreNLP(props);
    }

    public static StanfordCoreNLP AllTheThings() {
        Properties props = new Properties();
        props.put("annotators", "tokenize,ssplit,pos,lemma,ner,parse,entitymentions,dcoref,sentiment,quote");
        return new StanfordCoreNLP(props);
        /*
        try {
            Annotation annotation = new Annotation(contents);

            StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
            pipeline.annotate(annotation);

            this.myAnnotation = annotation;

            MyAnnotator m = new MyAnnotator(props);
            this.myDocument = m.getDocument(annotation);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    public static StanfordCoreNLP DCorefExtractionPipe() {
        Properties props = new Properties();
        props.put("annotators", "tokenize,ssplit,pos,lemma,ner,parse,dcoref");
        return new StanfordCoreNLP(props);
    }

    public static StanfordCoreNLP RelationExtractionPipe() {
        Properties props = new Properties();
        props.put("annotators", "tokenize,ssplit,pos,lemma,ner,depparse,relation");
        return new StanfordCoreNLP(props);
    }
}
