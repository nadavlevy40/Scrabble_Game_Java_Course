package testGameLogic;

import Model.GameLogic.CacheManager;
import Model.GameLogic.LRU;

public class testCacheManager {
    public static void main(String[] args) {
        testCacheManager t = new testCacheManager();
        t.testCacheManager();
        System.out.println("testCacheManager-done");
    }


    public void testCacheManager() {
        CacheManager exists = new CacheManager(3, new LRU());
        boolean b = exists.query("a");
        b |= exists.query("b");
        b |= exists.query("c");

        if (b) System.out.println("wrong result for CacheManager first queries");

        exists.add("a");
        exists.add("b");
        exists.add("c");

        b = exists.query("a");
        b &= exists.query("b");
        b &= exists.query("c");

        if (!b) System.out.println("wrong result for CacheManager second queries");

        boolean bf = exists.query("d"); // false, LRU is "a"
        exists.add("d");
        boolean bt = exists.query("d"); // true
        bf |= exists.query("a"); // false
        exists.add("a");
        bt &= exists.query("a"); // true, LRU is "b"

        if (bf || !bt) System.out.println("wrong result for CacheManager last queries");

    }

}
