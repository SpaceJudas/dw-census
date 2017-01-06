package gov.ankhmorpork.core.corenlp.Annotators;

import com.sun.org.apache.xml.internal.utils.UnImplNode;
import edu.stanford.nlp.ling.CoreAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.Annotator;
import edu.stanford.nlp.util.Comparators;
import edu.stanford.nlp.util.CoreMap;
import gov.ankhmorpork.core.BookText;
import gov.ankhmorpork.core.corenlp.BookPipeline;
import gov.ankhmorpork.core.util.Text;
import javafx.collections.transformation.SortedList;
import javafx.util.Pair;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static edu.stanford.nlp.pipeline.QuoteAnnotator.DIRECTED_QUOTES;

/**
 * Created by spooc on 1/4/2017.
 *
 * Death is not like the rest of the mere mortals in the series. He needs no quotation
 * marks. He speaks in ALLCAPS.
 */
public class DeathAnnotator implements Annotator {
    //1. find all death quotes (ALL-CAPS no quotes) -> surround them with quotes
    //2. put it into the analyzer
    //3. find all known death quotes that don't have a speaker. add ", said Death" to them
    //4. reanalyze

    public static HashSet<String> DEATHQUOTE_SENTENCE_END_CHARS;
    static {
        DEATHQUOTE_SENTENCE_END_CHARS = new HashSet<>();
        DEATHQUOTE_SENTENCE_END_CHARS.add(".");
        DEATHQUOTE_SENTENCE_END_CHARS.add("?");
        DEATHQUOTE_SENTENCE_END_CHARS.add("!");
    }


    private static String makeDashesNice(String text) {
        return text.replaceAll("\\p{Pd}", "-");
    }

    private static String makeNewlinesNice(String text) {
        return text.replaceAll("\\r\\n", "\\n");
    }

    public static String addQuotationMarks(String text) {
        text = makeDashesNice(text);
        text = makeNewlinesNice(text);

        Annotation textSegments = new Annotation(text);
        BookPipeline.Sentences().annotate(textSegments);

        String AllCapsWord = "\\b[A-Z'’-]+\\b";
        String WordSeperator = "[,]* ";
        String QuoteStarter = "\\b[A-Z'’]";
        String QuoteEnders = "[.,!?-\n\r]";

        //foreach sentence
            //find sequences of all-caps words
        String startQuote = "“";
        String endQuote = "”";

        //Add the positions of what needs quotes now.
        //Sorted by position of right quote ascending
        SortedList<Pair<Integer, Integer>> needsQuotesCharacterOffsets = new SortedList<Pair<Integer, Integer>>(null, Comparator.comparing(Pair::getValue));

        for (CoreMap sentence: textSegments.get(CoreAnnotations.SentencesAnnotation.class)) {
            String sText = sentence.get(CoreAnnotations.TextAnnotation.class);
            if (isSentenceDeathQuote(sText)) {
                // If an entire sentence is in caps, then it's almost certainly death
                // TODO: AND we can add ", said Death" after it
                System.out.println("Easy death quote find");
                System.out.println(sText);
                Pair<Integer, Integer> substringPositions = new Pair<Integer, Integer>(
                    sentence.get(CoreAnnotations.CharacterOffsetBeginAnnotation.class),
                    sentence.get(CoreAnnotations.CharacterOffsetEndAnnotation.class));
                needsQuotesCharacterOffsets.add(substringPositions);
            } //These may include "said death" already
            else if (containsPossibleCandidates(sentence)) {
                System.out.println("Candidate: "+sentence);
                List<CoreLabel> allCapsTokens = sentence.get(CoreAnnotations.TokensAnnotation.class).stream()
                    .filter(token -> Text.isAllCaps(token.get(CoreAnnotations.TextAnnotation.class))).collect(Collectors.toList());

                for (CoreLabel t: allCapsTokens) {
                    //TODO: continue from here tomorrow

                }

                //List<String> promisingSequences =
            }
        }
        //Now that we know their positions, create a string builder and replace them all
        StringBuilder newText = new StringBuilder(text);

        List<String> candidates = new ArrayList<String>();


        return newText.toString();
    }

    //Done. Requires user confirmation of generated "Death Quotes"
    private static boolean isSentenceDeathQuote(String text) {
        return Text.isAllCaps(text);
    }

    //TODO
    private static boolean containsPossibleCandidates(CoreMap sentence) {
        String text = sentence.get(CoreAnnotations.TextAnnotation.class);

        //TODO: check contents of words
        String[] words = text.split("\\b");

        int allCapsWordCount = 0;
        for (int i=0; i<words.length; i++) {
            //if text is all caps and it actually contains letters
            //TODO: replace the true
            if (Text.isAllCaps(words[i]) && true) {
                allCapsWordCount++;
            }
        }
        //TODO: revisit
        return allCapsWordCount > 2;
    }

    /**
     * Takes a string of text that is said by death and adds ",\" said Death" to the end.
     * @param text
     * @return
     */
    public static final HashSet<String> UNDIRECTED_QUOTES;
    static {
        UNDIRECTED_QUOTES = new HashSet<>();
        UNDIRECTED_QUOTES.add("\"");
        UNDIRECTED_QUOTES.add("'");
    }

    /**
     * Quote text must begin and end with quotation marks
     * @param quoteText
     * @return
     */
    private static String tryAddAttribution(String quoteText) {
        String newQuoteText = quoteText;
        String firstChar = ""+quoteText.charAt(0);
        String lastChar = ""+quoteText.charAt(quoteText.length()-1);
        String penultimateChar = ""+quoteText.charAt(quoteText.length()-2);

        //only do stuff if its actaully a quote
        if ((DIRECTED_QUOTES.containsKey(firstChar) && DIRECTED_QUOTES.get(firstChar).equals(lastChar))
            || (UNDIRECTED_QUOTES.contains(firstChar) && firstChar.equals(lastChar)))
        {
            StringBuilder s = new StringBuilder(quoteText);

            if ("!".equals(penultimateChar) || "?".equals(penultimateChar) || ",".equals(penultimateChar)) {
                //Don't add a comma
            }
            else if (".".equals(penultimateChar)) {
                //Replace the . with ,
                s.replace(s.length()-2,s.length()-2,",");
            } else {
                //add a comma before the quote
                s.insert(s.length()-1, ",");
            }

            s.append(" said Death.");

            newQuoteText = s.toString();
        }

        return newQuoteText;
    }

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
