package gov.ankhmorpork.test;

import com.google.gson.GsonBuilder;
import com.google.protobuf.ByteString;
import edu.stanford.nlp.io.StringOutputStream;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.AnnotationSerializer;
import edu.stanford.nlp.pipeline.ProtobufAnnotationSerializer;
import gov.ankhmorpork.core.BookText;
import gov.ankhmorpork.core.db.TextSource;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by spooc on 1/4/2017.
 */
public class ProtobufTest {
    public static void TestSimpleJsonConversion() {
        String text = TextSource.getWyrdSisters();
        BookText book = new BookText(text);
        BookText ch5 = new BookText(book.getChapters()[6]);

        Annotation a = ch5.getAnnotation();
        List<CoreLabel> tokens = a.get(CoreAnnotations.TokensAnnotation.class);


        //builder.registerTypeAdapter(TextAnnotation.class, new StringAnnotationAdapter());
        //builder.registerTypeAdapter(Annotation.class, new AnnotationTypeAdapter()).create().toJson(tokAnnot);
    }

    public static void TokenConvertTest() {
        String text = TextSource.getWyrdSisters();
        BookText book = new BookText(text);
        BookText ch5 = new BookText(book.getChapters()[6]);

        Annotation a = ch5.getAnnotation();

        List<CoreLabel> tokens = a.get(CoreAnnotations.TokensAnnotation.class);

        GsonBuilder builder = new GsonBuilder();
        String s = builder.create().toJson(tokens);
        System.out.println(s);
    }

    public static void AnnotationConvertTest() {
        String text = TextSource.getWyrdSisters();
        BookText book = new BookText(text);
        BookText ch5 = new BookText(book.getChapters()[6]);

        Annotation a = ch5.getAnnotation();
        AnnotationSerializer serializer = new ProtobufAnnotationSerializer();
        try {
            File outFile = new File("./data/test/AnnotationConvertTest.proto");
            outFile.createNewFile();

            try (OutputStream os = new FileOutputStream(outFile)) {
                serializer.write(a, os);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
