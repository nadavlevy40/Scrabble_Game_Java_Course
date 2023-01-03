package test;


import java.util.LinkedHashMap;

public class CacheManager {
    private final int size;
    private final CacheReplacementPolicy crp;
    private final LinkedHashMap<String, Integer> cache;

    public CacheManager(int size, CacheReplacementPolicy crp) {
        this.size = size;
        this.crp = crp;
        this.cache = new LinkedHashMap<>();
    }
//this method checks whether given word is in the cache - if so it updates the replacement policy and return true.
//else , return false
    public boolean query(String word) {
        if (cache.containsKey(word)) {
            crp.add(word);
            return true;
        }
        return false;
    }
//method to add word to the cache.
//if already in cache , its count incremented , else its its added with count of 1.
//if the cache size reaches over his maximum size,
//the cache replacement policy is used to determine which word to remove from the cache.
    public void add(String word) {
        crp.add(word);
        if (cache.containsKey(word)) {
            cache.put(word, cache.get(word) + 1);
        } else {
            cache.put(word, 1);
        }
        if (cache.size() > size) {
            String remove = crp.remove();
            cache.remove(remove);
        }
    }
}