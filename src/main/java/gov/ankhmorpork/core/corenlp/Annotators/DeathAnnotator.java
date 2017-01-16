package gov.ankhmorpork.core.corenlp.Annotators;

import edu.stanford.nlp.ling.CoreAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.*;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.SentenceUtils;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.Annotator;
import edu.stanford.nlp.util.ArrayCoreMap;
import edu.stanford.nlp.util.CoreMap;
import gov.ankhmorpork.core.corenlp.BookPipeline;
import gov.ankhmorpork.util.CoreNLP;
import gov.ankhmorpork.util.Quote;
import gov.ankhmorpork.util.Text;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static javax.swing.UIManager.get;

/**
 * Created by spooc on 1/4/2017.
 *
 * Death is not like the rest of the mere mortals in the series. He needs no quotation
 * marks. He speaks in ALLCAPS.
 *
 * This should
 */
public class DeathAnnotator implements Annotator {
    //4. reanalyze
    public static HashSet<String> DEATHQUOTE_SENTENCE_END_CHARS;

    static {
        DEATHQUOTE_SENTENCE_END_CHARS = new HashSet<>();
        DEATHQUOTE_SENTENCE_END_CHARS.add(".");
        DEATHQUOTE_SENTENCE_END_CHARS.add("?");
        DEATHQUOTE_SENTENCE_END_CHARS.add("!");
    }

    /**
     * Should consist of identifying death quotes and surrounding them in quotation marks if necessary.
     * @param text
     * @return
     */
    public String preprocess(String text) {
        Annotation splitText = new Annotation(text);
        BookPipeline.Sentences().annotate(splitText);

        List<CoreMap> probableDeathQuotes = this.getProbableDeathQuotes(splitText);
        //Now that we know their positions, create a string builder and replace them all
        Comparator<CoreMap> descendingCoreMapPositionComparator = (CoreMap a, CoreMap b) -> Integer.compare(b.get(CharacterOffsetEndAnnotation.class), a.get(CharacterOffsetEndAnnotation.class));

        StringBuilder newText = new StringBuilder(text);
        probableDeathQuotes.stream().sorted(descendingCoreMapPositionComparator).forEach(quote -> {
            Integer initialI = quote.get(CharacterOffsetBeginAnnotation.class);
            Integer finalI = quote.get(CharacterOffsetEndAnnotation.class);

            newText.insert(initialI, Quote.QUOTATION_INITIAL_MARK);
            newText.insert(finalI+1, Quote.QUOTATION_FINAL_MARK);
        });

        return newText.toString();
    }

    /**
     * 1. find all death quotes (ALL-CAPS no quotes) -> surround them with quotes
     * 2. put it into the analyzer
     * 3. find all known death quotes that don't have a speaker. add ", said Death" to them
     */
    @Override
    public void annotate(Annotation annotation) {
    }

    /**
     * After annotating, go back. Find all of our quotes. Add the Death attributions. Then run coref again
     *
     * @param originalDeathQuotes
     * @param a
     * @return
     */
    public String postprocess(Annotation a, List<CoreMap> originalDeathQuotes) {
        //The problem now with postprocessing is that the text given to annotation A has already been transformed,
        //originalText
        //We need to add , said Death to the end of stuff still
        String currentText = a.get(TextAnnotation.class);
        StringBuilder newText = new StringBuilder(currentText);

        //Quotes should be sorted in ascending order of position so we'll iterate backwards
        for (int i=originalDeathQuotes.size()-1; i<=0; i--) {
            List<CoreLabel> annotationTokens = a.get(TokensAnnotation.class);
            CoreMap thisQuote = originalDeathQuotes.get(i);
            int newBeginIndex = (i*2) + thisQuote.get(CharacterOffsetBeginAnnotation.class);
            int newEndIndex = (i*2) + 2 + thisQuote.get(CharacterOffsetEndAnnotation.class);
            int newBeginTokenIndex = (i*2) + thisQuote.get(TokenBeginAnnotation.class);
            int newEndTokenIndex = (i*2) + 2 + thisQuote.get(TokenEndAnnotation.class);

            List<CoreLabel> newQuoteTokens = annotationTokens.stream().skip(newBeginTokenIndex).limit(newEndTokenIndex - newBeginTokenIndex).collect(Collectors.toList());

            //Got no primary speaker? Attribute it with death.
            if (!CoreNLP.mentionIdHasCorefs(CoreNLP.getPrimarySpeakerId(newQuoteTokens)))
            {
                String newQuoteText = tryAddAttribution(newText.substring(newBeginIndex, newEndIndex));
                newText.replace(newBeginIndex, newEndIndex, newQuoteText);
            }
        }

        return newText.toString();
    }

    public List<CoreMap> getProbableDeathQuotes(Annotation annotation) {
        List<CoreMap> probableDeathQuotes = new ArrayList<>();

        for (CoreMap sentence: annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
            //String sText = sentence.get(CoreAnnotations.TextAnnotation.class);
            List<CoreLabel> tokens = sentence.get(TokensAnnotation.class);

            //Drop tokens with lowercase letters and tokens that are already part of a quotation
            List<CoreLabel> acceptedTokens = sentence.get(TokensAnnotation.class).stream()
                .filter(t -> Text.isAllCaps(t.get(TextAnnotation.class)))
                .filter(t -> !t.containsKey(QuotationIndexAnnotation.class))
                .collect(Collectors.toList());

            if (tokens.size() == acceptedTokens.size()) //Whole sentence is accepted
            {
                probableDeathQuotes.add(sentence);
            }
            else if (acceptedTokens.size() > 0) //The sentence has some possible deathspeak tokens.
            {
                //Group tokens into contiguous sets.
                List<CoreMap> candidates = groupContiguousTokens(acceptedTokens);
                //Add a begin and end annotation
                candidates.stream().forEach(c->{
                    List<CoreLabel> toks = c.get(TokensAnnotation.class);
                    CoreLabel first = toks.get(0);
                    CoreLabel last = toks.get(toks.size()-1);
                    //TODO: this is going to be pretty slow
                    c.set(TokenBeginAnnotation.class, annotation.get(TokensAnnotation.class).indexOf(first));
                    c.set(TokenEndAnnotation.class, annotation.get(TokensAnnotation.class).indexOf(last));
                });

                //Drop groups that don't have any actual letters
                //Drop groups that are too small
                //TODO: drop acronyms
                Pattern alphaCaps = Pattern.compile("[A-Z]");
                candidates = candidates.stream()
                    //possible quote groups should contain at least some letters
                    .filter( c-> alphaCaps.matcher(c.get(TextAnnotation.class)).find())
                    // possible death quotes will be at least 2 tokens in length as in the following example (punctution
                    .filter( c-> c.get(TokensAnnotation.class).size() >= 2)
                    .collect(Collectors.toList());

                probableDeathQuotes.addAll(candidates);
            }
        }

        return probableDeathQuotes;
    }

    /**
     * Creates a CoreMap with lists of the contiguous tokens
     * @param tokens
     * @return
     */
    public List<CoreMap> groupContiguousTokens(List<CoreLabel> tokens) {
        List<CoreMap> contiguousTokenGroups = new ArrayList<>();

        //build our candidate quotes from our tokens
        for (CoreLabel thisToken : tokens) {
            //Initialize empty arraylist for tokens if there are no tokens
            CoreMap currentGrouping = contiguousTokenGroups.isEmpty() ? null : contiguousTokenGroups.get(contiguousTokenGroups.size()-1);
            Integer thisTokenIndex = thisToken.get(IndexAnnotation.class);
            CoreLabel priorToken = currentGrouping==null || !currentGrouping.containsKey(TokensAnnotation.class) ?
                null : currentGrouping.get(TokensAnnotation.class).get(currentGrouping.get(TokensAnnotation.class).size()-1);

            if (currentGrouping == null || priorToken == null || thisTokenIndex != 1+priorToken.get(IndexAnnotation.class)) {
                //Either this is the first token, or it is not contiguous.
                currentGrouping = new ArrayCoreMap();
                currentGrouping.set(TokensAnnotation.class, new ArrayList<>());

                contiguousTokenGroups.add(currentGrouping);
            }
            currentGrouping.get(TokensAnnotation.class).add(thisToken);
        }

        //Set some metaproperties on the groups
        for (CoreMap quote: contiguousTokenGroups) {

            List<CoreLabel> quoteTokens = quote.get(TokensAnnotation.class);
            CoreLabel firstToken = quoteTokens.get(0);
            quote.set(CharacterOffsetBeginAnnotation.class, firstToken.get(CharacterOffsetBeginAnnotation.class));

            CoreLabel lastToken = quoteTokens.get(quoteTokens.size()-1);
            quote.set(CharacterOffsetEndAnnotation.class, lastToken.get(CharacterOffsetEndAnnotation.class));

            //"invertible=true" for PTBTokenizer happens by default. so i can turn tokens into string properly
            quote.set(TextAnnotation.class, SentenceUtils.listToOriginalTextString(quoteTokens));
        }
        return contiguousTokenGroups;
    }

    /**
     * Quote text must begin and end with quotation marks
     * @param quoteText
     * @return
     */
    private static String tryAddAttribution(String quoteText) {
        String newQuoteText = quoteText;

        //only do stuff if its actually a quote
        if (Quote.isSurroundedWithQuoteMarks(newQuoteText)) {
            String penultimateChar = ""+quoteText.charAt(quoteText.length()-2);
            StringBuilder s = new StringBuilder(quoteText);

            if ("!".equals(penultimateChar) || "?".equals(penultimateChar) || ",".equals(penultimateChar)) {
                //Don't add a comma
            }
            else if (".".equals(penultimateChar)) {
                //Replace the . with ,
                s.replace(s.length()-2,s.length()-1,",");
            } else {
                //add a , before the "
                s.insert(s.length()-1, ",");
            }
            s.append(" said Death.");

            newQuoteText = s.toString();
        }

        return newQuoteText;
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
