package test;

import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;
//implements a cache replacement policy called Least Frequently Used (LFU).
//It is a policy that removes the item in the cache that has been used the fewest number of times when the cache becomes full.
public class LFU implements CacheReplacementPolicy {
    private final LinkedHashMap<String, Integer>  wordFrequencyMap;
    private int currentMinFrequency;

    public LFU() {
        this. wordFrequencyMap = new LinkedHashMap<>();
        this.currentMinFrequency = 0;
    }

    public void add(String word) {
        if ( wordFrequencyMap.containsKey(word)) {
            wordFrequencyMap.put(word,  wordFrequencyMap.get(word) + 1);
        } else {
            wordFrequencyMap.put(word, 1);
        }
        currentMinFrequency = 1;
    }
//this method removes the word from the frequency map that has the current minimum frequency and returns it.
//then increments the current minimum frequency.
    public String remove() {
        Set<Entry<String, Integer>> entrySet =  wordFrequencyMap.entrySet();
        String remove = null;
        for (Entry<String, Integer> entry : entrySet) {
             if (entry.getValue() == currentMinFrequency) {
                remove = entry.getKey();
                break;
            }
        }
        wordFrequencyMap.remove(remove);
        currentMinFrequency++;
        return remove;
    }


}