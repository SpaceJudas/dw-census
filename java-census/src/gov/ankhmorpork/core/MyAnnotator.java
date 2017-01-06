package gov.ankhmorpork.core;

import edu.stanford.nlp.coref.CorefCoreAnnotations;
import edu.stanford.nlp.dcoref.Document;
import edu.stanford.nlp.dcoref.MentionExtractor;
import edu.stanford.nlp.dcoref.RuleBasedCorefMentionFinder;
import edu.stanford.nlp.dcoref.SieveCoreferenceSystem;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.DeterministicCorefAnnotator;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.semgraph.SemanticGraphFactory;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.PropertiesUtils;
import edu.stanford.nlp.util.logging.Redwood;

import java.util.*;
import java.util.function.Predicate;

/**
 * Created by spooc on 12/21/2016.
 */
public class MyAnnotator {
    private static final Redwood.RedwoodChannels log = Redwood.channels(new Object[]{DeterministicCorefAnnotator.class});
    private static final boolean VERBOSE = false;
    private final MentionExtractor mentionExtractor;
    private final SieveCoreferenceSystem corefSystem;
    private final boolean allowReparsing;

    public MyAnnotator(Properties props) throws Exception {
        try {
            this.corefSystem = new SieveCoreferenceSystem(props);
            this.mentionExtractor = new MentionExtractor(this.corefSystem.dictionaries(), this.corefSystem.semantics());
            this.allowReparsing = PropertiesUtils.getBool(props, "dcoref.allowReparsing", true);
        } catch (Exception var3) {
            System.out.println(new Object[]{"cannot create DeterministicCorefAnnotator!"});
            System.out.println(new Object[]{var3});
            throw new RuntimeException(var3);
        }
    }
    public Document getDocument(Annotation annotation) {
        try {
            ArrayList e = new ArrayList();
            ArrayList sentences = new ArrayList();
            boolean hasSpeakerAnnotations = false;
            if(!annotation.containsKey(CoreAnnotations.SentencesAnnotation.class)) {
                System.out.println(new Object[]{"this coreference resolution system requires SentencesAnnotation!"});
            } else {
                Iterator finder = ((List)annotation.get(CoreAnnotations.SentencesAnnotation.class)).iterator();

                while(finder.hasNext()) {
                    CoreMap allUnprocessedMentions = (CoreMap)finder.next();
                    List document = (List)allUnprocessedMentions.get(CoreAnnotations.TokensAnnotation.class);
                    sentences.add(document);
                    Tree orderedMentions = (Tree)allUnprocessedMentions.get(TreeCoreAnnotations.TreeAnnotation.class);
                    e.add(orderedMentions);
                    SemanticGraph result = SemanticGraphFactory.makeFromTree(orderedMentions, SemanticGraphFactory.Mode.COLLAPSED, GrammaticalStructure.Extras.NONE, (Predicate)null, true);
                    allUnprocessedMentions.set(SemanticGraphCoreAnnotations.AlternativeDependenciesAnnotation.class, result);
                    if(!hasSpeakerAnnotations) {
                        Iterator oldResult = document.iterator();

                        while(oldResult.hasNext()) {
                            CoreLabel t = (CoreLabel)oldResult.next();
                            if(t.get(CoreAnnotations.SpeakerAnnotation.class) != null) {
                                hasSpeakerAnnotations = true;
                                break;
                            }
                        }
                    }

                    MentionExtractor.mergeLabels(orderedMentions, document);
                    MentionExtractor.initializeUtterance(document);
                }

                if(hasSpeakerAnnotations) {
                    annotation.set(CoreAnnotations.UseMarkedDiscourseAnnotation.class, Boolean.valueOf(true));
                }

                RuleBasedCorefMentionFinder finder1 = new RuleBasedCorefMentionFinder(true);
                List allUnprocessedMentions1 = finder1.extractPredictedMentions(annotation, 0, this.corefSystem.dictionaries());
                Document document1 = this.mentionExtractor.arrange(annotation, sentences, e, allUnprocessedMentions1);
                List orderedMentions1 = document1.getOrderedMentions();
                Map result1 = this.corefSystem.corefReturnHybridOutput(document1);
                annotation.set(CorefCoreAnnotations.CorefChainAnnotation.class, result1);
                return document1;
            }
        } catch (RuntimeException var12) {
            throw var12;
        } catch (Exception var13) {
            throw new RuntimeException(var13);
        }
        return null;
    }
    public void annotate(Annotation annotation)
    {
        try {
            ArrayList e = new ArrayList();
            ArrayList sentences = new ArrayList();
            boolean hasSpeakerAnnotations = false;
            if(!annotation.containsKey(CoreAnnotations.SentencesAnnotation.class)) {
                System.out.println(new Object[]{"this coreference resolution system requires SentencesAnnotation!"});
            } else {
                Iterator finder = ((List)annotation.get(CoreAnnotations.SentencesAnnotation.class)).iterator();

                while(finder.hasNext()) {
                    CoreMap allUnprocessedMentions = (CoreMap)finder.next();
                    List document = (List)allUnprocessedMentions.get(CoreAnnotations.TokensAnnotation.class);
                    sentences.add(document);
                    Tree orderedMentions = (Tree)allUnprocessedMentions.get(TreeCoreAnnotations.TreeAnnotation.class);
                    e.add(orderedMentions);
                    SemanticGraph result = SemanticGraphFactory.makeFromTree(orderedMentions, SemanticGraphFactory.Mode.COLLAPSED, GrammaticalStructure.Extras.NONE, (Predicate)null, true);
                    allUnprocessedMentions.set(SemanticGraphCoreAnnotations.AlternativeDependenciesAnnotation.class, result);
                    if(!hasSpeakerAnnotations) {
                        Iterator oldResult = document.iterator();

                        while(oldResult.hasNext()) {
                            CoreLabel t = (CoreLabel)oldResult.next();
                            if(t.get(CoreAnnotations.SpeakerAnnotation.class) != null) {
                                hasSpeakerAnnotations = true;
                                break;
                            }
                        }
                    }

                    MentionExtractor.mergeLabels(orderedMentions, document);
                    MentionExtractor.initializeUtterance(document);
                }

                if(hasSpeakerAnnotations) {
                    annotation.set(CoreAnnotations.UseMarkedDiscourseAnnotation.class, Boolean.valueOf(true));
                }

                RuleBasedCorefMentionFinder finder1 = new RuleBasedCorefMentionFinder(true);
                List allUnprocessedMentions1 = finder1.extractPredictedMentions(annotation, 0, this.corefSystem.dictionaries());
                Document document1 = this.mentionExtractor.arrange(annotation, sentences, e, allUnprocessedMentions1);
                List orderedMentions1 = document1.getOrderedMentions();
                Map result1 = this.corefSystem.corefReturnHybridOutput(document1);
                annotation.set(CorefCoreAnnotations.CorefChainAnnotation.class, result1);
            }
        } catch (RuntimeException var12) {
            throw var12;
        } catch (Exception var13) {
            throw new RuntimeException(var13);
        }
    }
}
