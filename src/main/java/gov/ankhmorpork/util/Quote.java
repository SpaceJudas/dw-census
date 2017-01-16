package gov.ankhmorpork.util;

import java.util.HashSet;
import java.util.function.BiPredicate;
import java.util.regex.Pattern;

import static edu.stanford.nlp.pipeline.QuoteAnnotator.DIRECTED_QUOTES;

public class Quote {
    private static Pattern QUOTATION_PATTERN = Pattern.compile("\\p{Pi}|\\p{Pf}||\\p{Po}");
    private static Pattern QUOTATION_INITIAL_PATTERN = Pattern.compile("\\p{Pi}");
    private static Pattern QUOTATION_FINAL_PATTERN = Pattern.compile("\\p{Pf}");
    private static Pattern QUOTATION_UNDIRECTED_PATTERN = Pattern.compile("\\p{Po}");

    public static String QUOTATION_INITIAL_MARK = "“";
    public static String QUOTATION_FINAL_MARK = "”";
    public static String QUOTATION_MARK = "\"";

    public static final HashSet<String> ALL_QUOTES;
    public static final HashSet<String> UNDIRECTED_QUOTES;
    static {
        UNDIRECTED_QUOTES = new HashSet<>();
        UNDIRECTED_QUOTES.add("\"");
        UNDIRECTED_QUOTES.add("'");

        ALL_QUOTES = new HashSet<>(UNDIRECTED_QUOTES);
        ALL_QUOTES.addAll(DIRECTED_QUOTES.keySet());
        ALL_QUOTES.addAll(DIRECTED_QUOTES.values());
    }

    /**
     * Returns whether or not the given string contains a quote.
     * . Operates in O(N) time where N is the size of s.
     * @param s string to search for a quote in.
     * @return whether or not a quote could be found
     */
    public static boolean containsMatchingQuoteMarks(String s) {
        BiPredicate<String, String> sContainsMatchingQuoteMarks = (String initialMark, String finalMark) -> {
            int initIndex = s.indexOf(initialMark);
            return initIndex >= 0
                && initIndex < s.length() - 1
                && initIndex > s.indexOf(finalMark, initIndex);
        };

        return UNDIRECTED_QUOTES.stream().anyMatch(q -> sContainsMatchingQuoteMarks.test(q,q))
            || DIRECTED_QUOTES.entrySet().stream().anyMatch(e -> sContainsMatchingQuoteMarks.test(e.getKey(), e.getValue()));
    }

    public static String surroundWithDoubleQuoteMarks(String s) {
        return QUOTATION_INITIAL_MARK + s + QUOTATION_FINAL_MARK;
    }

    public static boolean isSurroundedWithQuoteMarks(String s) {
        return quoteMarksMatch(""+s.charAt(0), s.charAt(s.length()-1) + "");
    }

    /**
     * Returns null if there is no matching quote mark
     * @param initialMark
     * @return
     */
    public static String getMatchingQuoteMark(String initialMark) {
        return DIRECTED_QUOTES.containsKey(initialMark) ?
            DIRECTED_QUOTES.get(initialMark) :
            UNDIRECTED_QUOTES.contains(initialMark) ?
                initialMark :
                null;
    }

    public static boolean quoteMarksMatch(String initialMark, String finalMark) {
        return finalMark.equals(getMatchingQuoteMark(initialMark));
    }
}
