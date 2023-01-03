package test;

import java.util.LinkedHashMap;
//implements a cache replacement policy called Least Recently Used (LRU).
//that policy removes the item in the cache that has not been used for the longest time when the cache becomes full.
public class LRU implements CacheReplacementPolicy {
    private final LinkedHashMap<String, Integer> wordsRecentlyMap;

    public LRU() {
        this.wordsRecentlyMap = new LinkedHashMap<>();
    }

    public void add(String word) {
        if (wordsRecentlyMap.containsKey(word)) {
            wordsRecentlyMap.remove(word);
            wordsRecentlyMap.put(word, 1);
        } else {
            wordsRecentlyMap.put(word, 1);
        }
    }

    public String remove() {
        String remove = wordsRecentlyMap.keySet().iterator().next();
        wordsRecentlyMap.remove(remove);
        return remove;
    }


}