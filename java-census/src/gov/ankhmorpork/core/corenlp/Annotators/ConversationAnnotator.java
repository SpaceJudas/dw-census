package gov.ankhmorpork.core.corenlp.Annotators;

import edu.stanford.nlp.ling.CoreAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.Annotator;

import java.util.List;
import java.util.Set;

/**
 * Created by spooc on 1/2/2017.
 */
public class ConversationAnnotator implements Annotator{

    @Override
    public void annotate(Annotation annotation) {
        List<CoreLabel> tokens = annotation.get(CoreAnnotations.TokensAnnotation.class);
        //CoreAnnotations.ParagraphsAnnotation
    }

    public void annotate(String[] paragraphs) {

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
