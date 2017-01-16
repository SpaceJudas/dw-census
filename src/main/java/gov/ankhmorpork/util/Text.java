package gov.ankhmorpork.util;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Text {


    private static Pattern DASHES_PATTERN = Pattern.compile("\\p{Pd}");
    private static String DASH_MARK = "-";

    //TODO: this is busted right now
    @Deprecated
    private static Pattern NEWLINES_PATTERN = Pattern.compile("\\p{Pd}");

    public static boolean isAllCaps(String s) {
        return s.toUpperCase().equals(s);
    }

    static Pattern nonAlphaNums = Pattern.compile("[^A-Za-z0-9]");
    public static Matcher getAllNonAlphaNums(String text) {

        Set<Character> chars = new HashSet<>();
        return nonAlphaNums.matcher(text);
    }

    /*public static String makeDashesNice(String s) {
        return DASHES_PATTERN.matcher(s).replaceAll(DASH_MARK);
    }*/

    //TODO: SyllableCounter is based on a deterministic fallback method for
    //This will be needed for Fleisch-Kinkaid readability
    //private static SyllableCounter sc = new SyllableCounter();
    //public static int countSyllables(String text) {
    //    return sc.count(text);
    //}




}
