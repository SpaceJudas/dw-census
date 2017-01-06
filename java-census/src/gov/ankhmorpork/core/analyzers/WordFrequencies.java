package gov.ankhmorpork.core.analyzers;

import java.util.HashMap;

public class WordFrequencies extends HashMap<String, Integer>{
    public WordFrequencies() {
        super();
    }

    public void increment(String word) {
        if (!this.containsKey(word)) {
            this.put(word, 0);
        }
        this.put(word, this.get(word)+1);
    }

    public Integer count(String word) {
        return this.get(word);
    }

    public Integer total() {
        return this.values().stream().reduce((a, b) -> a+b).get();
    }
}
