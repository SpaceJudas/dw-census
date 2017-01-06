package gov.ankhmorpork.core.util;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.QuoteAnnotator;
import edu.stanford.nlp.util.CoreMap;
import eu.crydee.syllablecounter.SyllableCounter;

import java.util.List;

public class Text {

    private static SyllableCounter sc = new SyllableCounter();
    public static int countSyllables(String text) {
        return sc.count(text);
    }

    public static boolean isAllCaps(String s) {
        return s.toUpperCase().equals(s);
    }

    public static boolean containsQuote(String s) {
        return QuoteAnnotator.DIRECTED_QUOTES.entrySet().stream().anyMatch(
            q -> {
                int qOpenIndex = s.indexOf(q.getKey());
                return (qOpenIndex >= 0 && qOpenIndex > s.indexOf(q.getValue()));
            }
        );
    }
    public static boolean containsQuote(CoreMap m) {
        List<CoreLabel> tokens = m.get(CoreAnnotations.TokensAnnotation.class);

        return tokens.stream().anyMatch(t->t.get(CoreAnnotations.SpeakerAnnotation.class) != null);
    }
}
