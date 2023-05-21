package Model.Logic;

import java.io.BufferedReader;
import java.io.FileReader;


public class Dictionary {
    private final CacheManager existWords;
    private final CacheManager notExistWords;
    private final BloomFilter bloomFilter;
    private final String[] filesToSearch;

    public Dictionary(String... fileNames) {

        existWords = new CacheManager(400, new LRU());
        notExistWords  = new CacheManager(100, new LFU());
        bloomFilter = new BloomFilter(256, "MD5", "SHA1");
        filesToSearch = fileNames;

//here we pass through all the files of words,
//and for each one we split the words by spaces,
//and add all the words into the bloom filter .
        for (String fileName : fileNames) {
            try {
                FileReader fr = new FileReader(fileName);
                BufferedReader br = new BufferedReader(fr);
                String line;
                while ((line = br.readLine()) != null) {
                    String[] words = line.split(" ");
                    for (String word : words) {
                        bloomFilter.add(word);
                    }
                }
                fr.close();
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

//The query method takes a word as input and returns true if the word is found in the list of files,
// false otherwise.
    public boolean query(String word) {
        if (existWords.query(word)) {
            return true;
        }
        if (notExistWords.query(word)) {
            return false;
        }

        if (bloomFilter.contains(word)) {
            existWords.add(word);
            return true;
        } else {
            notExistWords.add(word);
            return false;
        }

    }

//since the BloomFilter is a Probability based DataStructure , his  results may not be 100% accuracy.
//This is where the challenge method comes in,
//as it allowing the user to call a "challenge" on the server negative result of query a word ,
//by forcing the server to make a deep search over all the files of words to check if given word exist.
    public boolean challenge(String word) {
        boolean isFound = false;
        try {
            isFound = IOSearcher.search(word, filesToSearch);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return isFound;
    }

}