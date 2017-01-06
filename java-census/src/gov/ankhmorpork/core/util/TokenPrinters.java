package gov.ankhmorpork.core.util;

import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.util.CoreMap;

/**
 * Created by spooc on 12/21/2016.
 */
public class TokenPrinters {
    public static void printPeople(Annotation a) {
        System.out.println("printPeople:");
        int entityCounter=0;
        int personCounter=0;
        for (CoreMap token : a.get(TokensAnnotation.class))
        {
            if (token.containsKey(NamedEntityTagAnnotation.class)) {
                entityCounter++;
            }
            if ("PERSON".equals(token.get(NamedEntityTagAnnotation.class)))
            {
                personCounter++;
                System.out.println(entityCounter+"\t"+personCounter+"\t"+token.get(TextAnnotation.class) + "\t");
            }
        }
    }
}
