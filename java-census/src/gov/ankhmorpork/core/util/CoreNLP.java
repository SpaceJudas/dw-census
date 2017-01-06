package gov.ankhmorpork.core.util;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.SpeakerAnnotation;
import edu.stanford.nlp.ling.CoreLabel;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Created by spooc on 1/2/2017.
 */
public class CoreNLP {
    public static void getPrimarySpeaker(List<CoreLabel> tokens) {
        getWordiestSpeaker(tokens);
    }

    /**
     * Speaker gets +1 for every token said. This is meant to be used in conjunction with quotes
     * to naively figure out who is actually talking.
     * @param tokens
     * @return
     */
    private static String getWordiestSpeaker(List<CoreLabel> tokens)
    {
        Map<String, Long> speakerTokenCounts = getSpeakerTokenCounts(tokens);
        Long max = speakerTokenCounts.values().stream().max(Long::compareTo).get();
        return speakerTokenCounts.entrySet().stream()
            .filter(entry -> max == entry.getValue())
            .findFirst().map(Map.Entry::getKey).get();

    }

    private static Set<String> getAllSpeakers(List<CoreLabel> tokens) {
        return getSpeakerTokenCounts(tokens).keySet();
    }

    public static Map<String, Long> getSpeakerTokenCounts(List<CoreLabel> tokens) throws IllegalArgumentException {
        List<CoreLabel> spokenTokens = tokens.stream().filter(token -> token.containsKey(SpeakerAnnotation.class)).collect(Collectors.toList());
        return Statistics.getFrequencies(spokenTokens, token -> token.get(SpeakerAnnotation.class));
    }
}
