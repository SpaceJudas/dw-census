package gov.ankhmorpork.core.corenlp;

import edu.stanford.nlp.ling.CoreAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.ErasureUtils;

import java.util.List;
import java.util.Set;

public class BookAnnotations {
    private BookAnnotations() { }

    /**
     * The CoreMap key for getting the chapters contained by an annotation.
     *
     * Based on edu.stanford.nlp.ling.CoreAnnotation.ParagraphsAnnotation
     *
     * This key is typically set only on document annotations.
     */
    public static class ChapterAnnotation implements CoreAnnotation<List<CoreMap>> {
        @Override
        public Class<List<CoreMap>> getType() {
            return ErasureUtils.uncheckedCast(List.class);
        }
    }

    public static class QuotationPrimarySpeakerAnnotation implements CoreAnnotation<String> {
        @Override
        public Class<String> getType() {
            return String.class;
        }
    }

    public static class QuotationAllSpeakersAnnotation implements CoreAnnotation<Set<String>> {
        @Override
        public Class<Set<String>> getType() { return ErasureUtils.uncheckedCast(Set.class); }
    }
}
