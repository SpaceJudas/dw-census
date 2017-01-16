package gov.ankhmorpork.core;

import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.AnnotationSerializer;
import edu.stanford.nlp.pipeline.ProtobufAnnotationSerializer;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by spooc on 1/4/2017.
 */
public class AnnotationCache {
    private static final Path cacheDir = Paths.get("./data/cache/annotations");
    private static final ProtobufAnnotationSerializer serializer = new ProtobufAnnotationSerializer();

    public static boolean save(String name, Annotation a) {
        try {
            File outFile = new File(getCacheFilePath(name));
            outFile.createNewFile();

            try (OutputStream os = new FileOutputStream(outFile)) {
                serializer.write(a, os);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static Annotation load(String name) {
        Annotation annotation = null;
        try {
            annotation = serializer.readUndelimited(new File(getCacheFilePath(name)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return annotation;
    }

    private static String getCacheFilePath(String name) {
        return cacheDir.resolve(name+".proto").toString();
    }
}
