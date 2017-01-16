package gov.ankhmorpork.util;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.SpeakerAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.util.CoreMap;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Created by spooc on 1/2/2017.
 */
public class CoreNLP {
    public static String getPrimarySpeakerId(CoreMap m) {
        return getPrimarySpeakerId(m.get(CoreAnnotations.TokensAnnotation.class));
    }
    public static String getPrimarySpeakerId(List<CoreLabel> tokens) {
        return getWordiestSpeaker(tokens);
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

    private static String coreMapToString(CoreMap cm) {
        HashMap<String, String> ret = new HashMap<>();

        for (Class key : cm.keySet()) {
            Object val = cm.get(key);
            String valStr = "";
            if (val.getClass().equals(CoreLabel.class)) {
                valStr = coreMapToString((CoreLabel) val);
            } else if (val.getClass().equals(CoreMap.class)) {
                valStr = coreMapToString((CoreMap) val);
            } else {
                valStr = val.toString();
            }
            ret.put(val.toString(), valStr);
        }
        return ret.toString();
    }

    /**
     * Should more or less work. needs test
     * @param m
     * @return
     */
    public static boolean containsSpeakerAnnotation(CoreMap m) {
        List<CoreLabel> tokens = m.get(CoreAnnotations.TokensAnnotation.class);

        return tokens.stream().anyMatch(t->t.get(CoreAnnotations.SpeakerAnnotation.class) != null);
    }

    public static boolean mentionIdHasCorefs(String possibleMentionId) {
        try {
            Integer.parseInt(possibleMentionId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
