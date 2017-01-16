package gov.ankhmorpork.core.corenlp.Annotators;

import edu.stanford.nlp.ling.CoreAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.Annotator;

import java.util.Set;

/**
 * Created by spooc on 1/4/2017.
 */
public class ChapterAnnotator implements Annotator {
    private String ChapterAnnotator = "\n{3,}";

    @Override
    public void annotate(Annotation annotation) {

    }

    @Override
    public Set<Class<? extends CoreAnnotation>> requirementsSatisfied() {
        return null;
    }

    @Override
    public Set<Class<? extends CoreAnnotation>> requires() {
        return null;
    }
}
