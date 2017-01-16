package gov.ankhmorpork.core.corenlp.Annotators;

import edu.stanford.nlp.ling.CoreAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.QuotationsAnnotation;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.util.CoreMap;

import java.util.List;
import java.util.Properties;
import java.util.Set;

import static javax.swing.UIManager.get;

public class CustomQuoteAnnotator implements Annotator {
    public static final String DEFAULT_ANNOTATOR_NAME = "quotationplus";

    //Default to false, configured as name.manyspeakers
    private boolean includePrimarySpeaker;
    private boolean includeAllSpeakers;

    public CustomQuoteAnnotator() {
        this(null, null);
    }

    public CustomQuoteAnnotator(String name, Properties props) {
        name = (name==null) ? DEFAULT_ANNOTATOR_NAME : name;
        props = (props==null) ? new Properties() : props;

        this.includePrimarySpeaker = Boolean.parseBoolean(props.getProperty(name+".annotateprimary", "true"));
        this.includeAllSpeakers = Boolean.parseBoolean(props.getProperty(name+".", "true"));
    }



    /**
     * @param annotation
     */
    @Override
    public void annotate(Annotation annotation) {
        if (annotation.containsKey(QuotationsAnnotation.class)) {
            List<CoreMap> quotes = annotation.get(QuotationsAnnotation.class);
            for (CoreMap quote : quotes) {

            }
        }else {
            String msg = "CustomQuoteAnnotator: unable to find quottes in annotation " + annotation;
            System.err.println(msg);
            throw new IllegalArgumentException(msg);
        }

    }

    /**
     * TODO: implement this
     */
    @Override
    public Set<Class<? extends CoreAnnotation>> requirementsSatisfied() {
        return null;
    }

    /**
     * TODO: implement this
     */
    @Override
    public Set<Class<? extends CoreAnnotation>> requires() {
        return null;
    }
}
