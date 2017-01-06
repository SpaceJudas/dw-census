package gov.ankhmorpork.core.models;

import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.util.CoreMap;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by spooc on 12/22/2016.
 */
public class Speaker {
    public final UUID uuid;
    private List<String> names;
    private CorefChain corefs;
    private List<CoreMap> mentions;

    public Speaker() {
        uuid = UUID.randomUUID();
        names = new ArrayList<>();

    }

    public void addMention() {

    }

    public String getName() {
        throw new NotImplementedException();
    }
    public List<String> getAliases() {
        throw new NotImplementedException();
    }
    public List<String> getQuotes() {
        throw new NotImplementedException();
    }
}
