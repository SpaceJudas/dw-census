package gov.ankhmorpork.core.analyzers;

import eu.crydee.syllablecounter.SyllableCounter;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by spooc on 1/2/2017.
 *
 * SyllableCounter libary is based on a fallback method.
 *
 * Consider using http://morphadorner.northwestern.edu/
 * or http://alias-i.com/lingpipe/demos/tutorial/hyphenation/read-me.html
 *
 *
 */
public class SyllableAnalyzer {
    private static SyllableCounter sc = new SyllableCounter();

    public static int count(String word) {
        return sc.count(word);
    }
}
