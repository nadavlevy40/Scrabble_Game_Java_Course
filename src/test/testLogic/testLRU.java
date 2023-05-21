package testGameLogic;

import Model.GameLogic.CacheReplacementPolicy;
import Model.GameLogic.LRU;

public class testLRU {
    public static void main(String[] args) {
        testLRU t = new testLRU();
        t.testLRU();
        System.out.println("testLRU-done");
    }


    public void testLRU() {
        CacheReplacementPolicy lru = new LRU();
        lru.add("a");
        lru.add("b");
        lru.add("c");
        lru.add("a");

        if (!lru.remove().equals("b")) System.out.println("wrong implementation for LRU");
    }
}
