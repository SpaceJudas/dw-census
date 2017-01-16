package gov.ankhmorpork.core.analyzers;

import edu.stanford.nlp.coref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.coref.data.CorefChain.CorefMention;
import edu.stanford.nlp.pipeline.Annotation;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Wraps an annotation to make it easy to find speakers by their mention id.
 */
public class MentionsLookup {
    private Annotation baseAnnotation;
    //Maps mentionId to Mention object
    private Map<Integer, CorefMention> mentionMap = new HashMap<>();

    public MentionsLookup(Annotation a) {
        this.baseAnnotation = a;

        Collection<CorefChain> corefChains = a.get(CorefChainAnnotation.class).values();

        for (CorefChain corefChain: corefChains) {
            CorefMention representativeMention = corefChain.getRepresentativeMention();
            List<CorefMention> mentions = corefChain.getMentionsInTextualOrder();
            for (CorefMention mention: mentions) {
                Integer mentionId = mention.mentionID;
                mentionMap.put(mentionId, mention);
            }
        }
    }

    public CorefMention getMention(Integer mentionId) {
        return mentionMap.get(mentionId);
    }

    public CorefMention getRepresentativeMention(Integer mentionId) {
        Map<Integer, CorefChain> corefChains = this.baseAnnotation.get(CorefChainAnnotation.class);
        Integer corefChainId = mentionMap.get(mentionId).corefClusterID;

        return corefChains.get(corefChainId).getRepresentativeMention();
    }
}
