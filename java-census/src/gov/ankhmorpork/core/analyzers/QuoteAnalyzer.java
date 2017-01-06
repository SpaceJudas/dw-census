package gov.ankhmorpork.core.analyzers;

import edu.stanford.nlp.dcoref.Document;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import gov.ankhmorpork.core.MyAnnotator;

import java.util.List;
import java.util.Properties;

/**
 * Created by spooc on 12/21/2016.
 */
public class QuoteAnalyzer {
    private Annotation myAnnotation;
    private Document myDocument;

    public QuoteAnalyzer(String contents) {
        Properties props = new Properties();
        //props.put("annotators", "tokenize,ssplit,pos,lemma,ner,parse,mention,entitymentions,dcoref,sentiment,quote,relation");
        props.put("annotators", "tokenize,ssplit,pos,lemma,ner,parse,mention,entitymentions,dcoref,sentiment,quote,relation");
        try {
            Annotation annotation = new Annotation(contents);

            StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
            pipeline.annotate(annotation);

            this.myAnnotation = annotation;

            MyAnnotator m = new MyAnnotator(props);
            this.myDocument = m.getDocument(annotation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<CoreMap> getQuotes() {
        return myAnnotation.get(CoreAnnotations.QuotationsAnnotation.class);
    }

    public Annotation getAnnotation() {
        return myAnnotation;
    }
}
